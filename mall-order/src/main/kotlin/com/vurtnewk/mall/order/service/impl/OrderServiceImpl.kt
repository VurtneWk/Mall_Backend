package com.vurtnewk.mall.order.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.order.constants.OrderConstants
import com.vurtnewk.mall.order.dao.OrderDao
import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.feign.CartFeignService
import com.vurtnewk.mall.order.feign.MemberFeignService
import com.vurtnewk.mall.order.feign.WareFeignService
import com.vurtnewk.mall.order.interceptor.LoginUserInterceptor
import com.vurtnewk.mall.order.service.OrderService
import com.vurtnewk.mall.order.vo.OrderConfirmVo
import kotlinx.coroutines.*
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.UUID
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis


@Service("orderService")
class OrderServiceImpl(
    private val memberFeignService: MemberFeignService,
    private val cartFeignService: CartFeignService,
    private val executors: ThreadPoolExecutor,
    private val wareFeignService: WareFeignService,
    private val redisTemplate: StringRedisTemplate,
) : ServiceImpl<OrderDao, OrderEntity>(), OrderService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderEntity>().getPage(params),
            QueryWrapper()
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
                orderConfirmVo.stocks = wareFeignService.getSkusHasStock(
                    orderConfirmVo.items.map { it.skuId })
                    .data
                    ?.associate { it.skuId to it.hasStock } ?: emptyMap()

            }
        }
//        orderConfirmVo.address = memberFeignService.getAddress(memberRespVo.id!!)
//        orderConfirmVo.items = cartFeignService.getCurrentUserCartItems()
        orderConfirmVo.integration = memberRespVo.integration

        // 防重令牌 前端一份、后端一份
        val token = UUID.randomUUID().toString().replace("_", "")
        orderConfirmVo.orderToken = token
        redisTemplate.opsForValue().set(
            OrderConstants.USER_ORDER_TOKEN_PREFIX + memberRespVo.id,
            token, 30, TimeUnit.MINUTES
        )

        logInfo("orderConfirmVo ===> $orderConfirmVo")
        return orderConfirmVo
    }
}