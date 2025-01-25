package com.vurtnewk.mall.order.service.impl

import com.alibaba.fastjson2.TypeReference
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.R
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.order.constants.OrderConstants
import com.vurtnewk.mall.order.dao.OrderDao
import com.vurtnewk.mall.order.dao.OrderItemDao
import com.vurtnewk.mall.order.dto.OrderCreateDto
import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.entity.OrderItemEntity
import com.vurtnewk.mall.order.feign.CartFeignService
import com.vurtnewk.mall.order.feign.MemberFeignService
import com.vurtnewk.mall.order.feign.ProductFeignService
import com.vurtnewk.mall.order.feign.WareFeignService
import com.vurtnewk.mall.order.interceptor.LoginUserInterceptor
import com.vurtnewk.mall.order.service.OrderItemService
import com.vurtnewk.mall.order.service.OrderService
import com.vurtnewk.mall.order.vo.*
import kotlinx.coroutines.*
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.math.abs


@Service("orderService")
class OrderServiceImpl(
    private val memberFeignService: MemberFeignService,
    private val cartFeignService: CartFeignService,
    private val executors: ThreadPoolExecutor,
    private val wareFeignService: WareFeignService,
    private val redisTemplate: StringRedisTemplate,
    private val productFeignService: ProductFeignService,
    private val orderItemService: OrderItemService,
) : ServiceImpl<OrderDao, OrderEntity>(), OrderService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderEntity>().getPage(params), QueryWrapper()
        )
        return PageUtils(page)
    }

    override suspend fun confirmOrder(): OrderConfirmVo {
        val orderConfirmVo = OrderConfirmVo()
        val memberRespVo = LoginUserInterceptor.loginUserThreadLocal.get()
        //这个是处理异步线程是丢失 threadLocal 数据
        val attributes = RequestContextHolder.getRequestAttributes()
        coroutineScope {
            launch(executors.asCoroutineDispatcher()) {
                // 开始这个协程之前重新设置进去
                // 如果launch 不添加 executors.asCoroutineDispatcher() ， 有可能不会报错，因为多个协程可能会在同一个线程
                // 添加 executors.asCoroutineDispatcher() 之后就会在指定线程，而不是当前 springmvc里的线程
                RequestContextHolder.setRequestAttributes(attributes)
                orderConfirmVo.address = memberFeignService.getAddress(memberRespVo.id!!)
            }
            launch(executors.asCoroutineDispatcher()) {
                RequestContextHolder.setRequestAttributes(attributes)
                orderConfirmVo.items = cartFeignService.getCurrentUserCartItems()
                //查询是否有库存
                orderConfirmVo.stocks =
                    wareFeignService.getSkusHasStock(orderConfirmVo.items.map { it.skuId }).data?.associate { it.skuId to it.hasStock } ?: emptyMap()

            }
        }
//        orderConfirmVo.address = memberFeignService.getAddress(memberRespVo.id!!)
//        orderConfirmVo.items = cartFeignService.getCurrentUserCartItems()
        orderConfirmVo.integration = memberRespVo.integration

        // 防重令牌 前端一份、后端一份
        val token = UUID.randomUUID().toString().replace("_", "")
        orderConfirmVo.orderToken = token
        redisTemplate.opsForValue().set(
            OrderConstants.USER_ORDER_TOKEN_PREFIX + memberRespVo.id, token, 30, TimeUnit.MINUTES
        )

        logInfo("orderConfirmVo ===> $orderConfirmVo")
        return orderConfirmVo
    }

    /**
     * 提交订单
     * 1. 验证令牌
     *
     * lua 语法
     *
     * ```lua
     * if <condition> then
     *     -- 执行代码块
     * else
     *     -- 可选的 else 块
     * end
     * ```
     * lua 脚本
     * ```
     * if redis.call('get', KEYS[1]) == ARGV[1] then
     *     return redis.call('del', KEYS[1]) -- 如果条件成立，删除 key 并返回结果
     * else
     *     return 0 -- 如果条件不成立，返回 0
     * end
     * ```
     *
     *
     */
    @Transactional
    override suspend fun submitOrder(orderSubmitVo: OrderSubmitVo): SubmitOrderResponseVo {
        val memberRespVo = LoginUserInterceptor.loginUserThreadLocal.get()
        val vo = SubmitOrderResponseVo()
        //1、 验证令牌
        val tokenKey = OrderConstants.USER_ORDER_TOKEN_PREFIX + memberRespVo.id
        //令牌的删除 必须保证原子性
        // 如果删成功返回1 ， 其它返回0
        val script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end"
        val redisResult = redisTemplate.execute(DefaultRedisScript(script, Long::class.java), listOf(tokenKey), orderSubmitVo.orderToken)
        if (redisResult == 0L) {
            // 验证失败
            vo.code = 1
            return vo
        }
        // 2、创建订单 这里的参数 orderSubmitVo 在教程放在了 ThreadLocal 里来进行传递
        val orderCreateDto = createOrder(orderSubmitVo)
        // 3、验价
        if (abs(orderCreateDto.order.payAmount!!.subtract(orderSubmitVo.payPrice).toDouble()) >= 0.01) {
            // 验价失败
            vo.code = 2
            return vo
        }
        // 4、保存订单
        saveOrder(orderCreateDto)
        // 5、库存锁定 只要有异常回滚订单数据
        val wareSkuLockVo = WareSkuLockVo()
        wareSkuLockVo.orderSn = orderCreateDto.order.orderSn!!
        wareSkuLockVo.locks = orderCreateDto.orderItems.map {
            OrderItemVo().apply {
                skuId = it.skuId!!
                count = it.skuQuantity!!
                title = it.skuName
            }
        }
        var r: R = R.error()
        coroutineScope {
            launch(executors.asCoroutineDispatcher()) {
                r = wareFeignService.orderLockStock(wareSkuLockVo)
            }
        }
        if (r.isSuccess()) {

        }

        return vo
    }

    /**
     * 保存订单
     */
    private fun saveOrder(orderCreateDto: OrderCreateDto) {
        orderCreateDto.order.modifyTime = Date()
        this.save(orderCreateDto.order)
        orderItemService.saveBatch(orderCreateDto.orderItems)
    }

    private suspend fun createOrder(orderSubmitVo: OrderSubmitVo): OrderCreateDto {
        val orderCreateDto = OrderCreateDto()
        // 创建订单号
        val orderSn = IdWorker.getTimeId()
        coroutineScope {
            withContext(executors.asCoroutineDispatcher()) {
                launch {
                    // 生成订单号
                    orderCreateDto.order = buildOrder(orderSn, orderSubmitVo)
                }
                launch {
                    //生成购物项
                    orderCreateDto.orderItems = buildOrderItems(orderSn)
                }
            }
        }
        // 设置价格价格
        computePrice(orderCreateDto.order, orderCreateDto.orderItems)
        return orderCreateDto
    }

    /**
     * 计算价格
     */
    private fun computePrice(orderEntity: OrderEntity, orderItemEntities: List<OrderItemEntity>) {
        //总价
        var total = BigDecimal("0.0")
        //优惠价
        var coupon = BigDecimal("0.0")
        var intergration = BigDecimal("0.0")
        var promotion = BigDecimal("0.0")
        //积分、成长值
        var integrationTotal = 0
        var growthTotal = 0

        //订单总额，叠加每一个订单项的总额信息
        for (orderItem in orderItemEntities) {
            //优惠价格信息
            coupon = coupon.add(orderItem.couponAmount)
            promotion = promotion.add(orderItem.promotionAmount)
            intergration = intergration.add(orderItem.integrationAmount)

            //总价
            total = total.add(orderItem.realAmount)

            //积分信息和成长值信息
            integrationTotal += orderItem.giftIntegration ?: 0
            growthTotal += orderItem.giftGrowth ?: 0
        }
        //1、订单价格相关的
        orderEntity.totalAmount = total
        //设置应付总额(总额+运费)
        orderEntity.payAmount = total.add(orderEntity.freightAmount)
        orderEntity.couponAmount = coupon
        orderEntity.promotionAmount = promotion
        orderEntity.integrationAmount = intergration

        //设置积分成长值信息
        orderEntity.integration = integrationTotal
        orderEntity.growth = growthTotal
    }

    /**
     * 构建订单数据
     */
    private fun buildOrder(orderSn: String, orderSubmitVo: OrderSubmitVo): OrderEntity {
        val orderEntity = OrderEntity()
        orderEntity.orderSn = orderSn
        orderEntity.memberId = LoginUserInterceptor.loginUserThreadLocal.get().id
        // 获取收货地址信息
        val fare = wareFeignService.getFare(orderSubmitVo.addrId)
        val fareResp = fare.getData(object : TypeReference<FareVo>() {})
        fareResp?.let {
            //设置收货人信息
            with(orderEntity) {
                freightAmount = it.fare
                receiverCity = it.address?.city.orEmpty()
                receiverDetailAddress = it.address?.detailAddress.orEmpty()
                receiverName = it.address?.name.orEmpty()
                receiverPhone = it.address?.phone.orEmpty()
                receiverPostCode = it.address?.postCode.orEmpty()
                receiverProvince = it.address?.province.orEmpty()
                receiverRegion = it.address?.region.orEmpty()
            }
        }
        //设置删除状态(0-未删除，1-已删除)
        orderEntity.deleteStatus = 0
        return orderEntity
    }

    /**
     * 构建所有订单项
     */
    private fun buildOrderItems(orderSn: String): List<OrderItemEntity> {
        // 远程查出所有订单项数据
        val currentUserCartItems = cartFeignService.getCurrentUserCartItems()
        return currentUserCartItems.map {
            buildOrderItem(orderSn, it)
        }
    }

    /**
     * 构建订单项
     */
    private fun buildOrderItem(orderSn: String, orderItemVo: OrderItemVo): OrderItemEntity {
        val orderItemEntity = OrderItemEntity()
        // 1、订单信息
        orderItemEntity.orderSn = orderSn
        // 2、spu信息
        val spuInfoVo = productFeignService.getSpuInfoBySkuId(orderItemVo.skuId).getData(object : TypeReference<SpuInfoVo>() {})!!
        orderItemEntity.spuId = spuInfoVo.id
        orderItemEntity.spuBrand = spuInfoVo.brandId.toString()
        orderItemEntity.spuName = spuInfoVo.spuName
        orderItemEntity.categoryId = spuInfoVo.catalogId

        // 3、sku信息
        orderItemEntity.skuId = orderItemVo.skuId
        orderItemEntity.skuName = orderItemVo.title
        orderItemEntity.skuPic = orderItemVo.image
        orderItemEntity.skuPrice = orderItemVo.price
        orderItemEntity.skuAttrsVals = orderItemVo.skuAttr.joinToString(";")
        orderItemEntity.skuQuantity = orderItemVo.count
        // 4、优惠信息 不做
        // 5、积分信息
        orderItemEntity.giftGrowth = orderItemVo.price.multiply(BigDecimal(orderItemVo.count.toString())).toInt()
        orderItemEntity.giftIntegration = orderItemVo.price.multiply(BigDecimal(orderItemVo.count.toString())).toInt()
        // 6、订单项的价格信息
        orderItemEntity.promotionAmount = BigDecimal.ZERO
        orderItemEntity.couponAmount = BigDecimal.ZERO
        orderItemEntity.integrationAmount = BigDecimal.ZERO
        //当前订单项的实际金额
        val originPrice = orderItemEntity.skuPrice!!.multiply(BigDecimal(orderItemEntity.skuQuantity.toString()))
        orderItemEntity.realAmount =
            originPrice.subtract(orderItemEntity.promotionAmount).subtract(orderItemEntity.couponAmount).subtract(orderItemEntity.integrationAmount)

        return orderItemEntity
    }
}