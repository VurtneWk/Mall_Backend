package com.vurtnewk.mall.ware.service.impl

import com.alibaba.fastjson2.TypeReference
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.constants.OrderStatus
import com.vurtnewk.common.dto.mq.StockLockedDetail
import com.vurtnewk.common.dto.mq.StockLockedDto
import com.vurtnewk.common.excetion.NoStockException
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.logError
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.ware.constants.MQConstants
import com.vurtnewk.mall.ware.constants.WareLockStatus
import com.vurtnewk.mall.ware.dao.WareSkuDao
import com.vurtnewk.mall.ware.entity.WareOrderTaskDetailEntity
import com.vurtnewk.mall.ware.entity.WareOrderTaskEntity
import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.vurtnewk.mall.ware.feign.OrderFeignService
import com.vurtnewk.mall.ware.feign.ProductFeignService
import com.vurtnewk.mall.ware.service.WareOrderTaskDetailService
import com.vurtnewk.mall.ware.service.WareOrderTaskService
import com.vurtnewk.mall.ware.service.WareSkuService
import com.vurtnewk.mall.ware.vo.OrderVo
import com.vurtnewk.mall.ware.vo.SkuHasStockVo
import com.vurtnewk.mall.ware.vo.WareSkuLockVo
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("wareSkuService")
class WareSkuServiceImpl(
    private val productFeignService: ProductFeignService,
    private val rabbitTemplate: RabbitTemplate,
    private val wareOrderTaskService: WareOrderTaskService,
    private val wareOrderTaskDetailService: WareOrderTaskDetailService,
    private val orderFeignService: OrderFeignService,
) : ServiceImpl<WareSkuDao, WareSkuEntity>(), WareSkuService {

    /**
     * ## 查询商品库存
     * ### 参数
     * ```json
     * {
     *    page: 1,//当前页码
     *    limit: 10,//每页记录数
     *    sidx: 'id',//排序字段
     *    order: 'asc/desc',//排序方式
     *    wareId: 123,//仓库id
     *    skuId: 123//商品id
     * }
     * ```
     */
    override fun queryPage(params: Map<String, Any>): PageUtils {
        val wareId = params["wareId"] as? String
        val skuId = params["skuId"] as? String
        return KtQueryChainWrapper(WareSkuEntity::class.java)
            .eq(!wareId.isNullOrBlank(), WareSkuEntity::wareId, wareId)
            .eq(!skuId.isNullOrBlank(), WareSkuEntity::skuId, skuId)
            .toPage(params)
            .pageUtils()
    }

    @Transactional
    override fun addStock(skuId: Long, wareId: Long, skuNum: Int) {
        //先查表中是否有数据，如果有的话 就修改 没有就新增
        val wareSkuEntities = KtQueryChainWrapper(WareSkuEntity::class.java)
            .eq(WareSkuEntity::skuId, skuId)
            .eq(WareSkuEntity::wareId, wareId)
            .list()

        if (wareSkuEntities.isNullOrEmpty()) {
            val wareSkuEntity = WareSkuEntity().apply {
                this.skuId = skuId
                this.wareId = wareId
                this.stock = skuNum
                this.stockLocked = 0
            }
            //查询名字
            runCatching {
                val r = productFeignService.info(skuId)
                if (r.isSuccess()) {
                    wareSkuEntity.skuName = (r["skuInfo"] as Map<*, *>)["skuName"] as String
                }
            }.onSuccess {
                logInfo("远程查询商品名字成功 ${wareSkuEntity.skuName}")
            }.onFailure {
                logError("远程查询商品名字失败 ${it.message}")
            }
            this.baseMapper.insert(wareSkuEntity)
        } else {
            this.baseMapper.updateStock(skuId, wareId, skuNum)
        }


    }

    override fun getSkusHasStock(skuIds: List<Long>): List<SkuHasStockVo> {
        return skuIds.map { skuId ->
            val skuHasStockVo = SkuHasStockVo()
            //查询总库存
            val count = this.baseMapper.getSkuStock(skuId)
            skuHasStockVo.skuId = skuId
            skuHasStockVo.hasStock = (count ?: 0) > 0
            skuHasStockVo
        }
    }

    /**
     * 锁定库存
     *
     * (rollbackFor = [NoStockException::class])
     * 不加，默认是运行时异常都会回滚
     *
     * 库存解锁的场景
     * 1. 下订单成功，超时未支付或用户手动取消
     * 2. 下订单成功，锁定库存成功，但是后续业务异常，需要回滚
     *
     */
    @Transactional
    override fun orderLockStock(vo: WareSkuLockVo): Boolean {
        /**
         * 保存库存工作单的详情
         * 用于 追溯
         */
        val wareOrderTaskEntity = WareOrderTaskEntity(orderSn = vo.orderSn)
        wareOrderTaskService.save(wareOrderTaskEntity)


        //1、正常情况应该是按照下单的收货地址，找一个就近库存，锁定库存
        // 遍历所有商品信息，查询每个商品再那些仓库有库存
        val skuWareHasStocks = vo.locks.map {
            val skuWareHasStock = SkuWareHasStock()
            skuWareHasStock.skuId = it.skuId
            skuWareHasStock.wareIds = this.baseMapper.listWareIdHasSkuStock(it.skuId)
            skuWareHasStock.num = it.count
            skuWareHasStock
        }
        skuWareHasStocks.forEach { skuWareHasStock ->
            if (skuWareHasStock.wareIds.isEmpty()) {
                throw NoStockException(skuWareHasStock.skuId)
            }
            var skuStocked = false
            for (wareId in skuWareHasStock.wareIds) {
                val count = this.baseMapper.lockSkuStock(skuWareHasStock.skuId, wareId, skuWareHasStock.num)
                if (count == 1L) {
                    val wareOrderTaskDetailEntity = WareOrderTaskDetailEntity(
                        skuId = skuWareHasStock.skuId,
                        skuNum = skuWareHasStock.num,
                        wareId = wareId,
                        lockStatus = WareLockStatus.WARE_STATUS_LOCKED
                    )

                    wareOrderTaskDetailService.save(wareOrderTaskDetailEntity)
                    //
                    val stockLockedDetail = StockLockedDetail()
                    BeanUtils.copyProperties(wareOrderTaskDetailEntity, stockLockedDetail)
                    val stockLockedDto = StockLockedDto(wareOrderTaskEntity.id!!, stockLockedDetail)

                    rabbitTemplate.convertAndSend(MQConstants.Exchange.STOCK_EVENT, MQConstants.RoutingKey.STOCK_LOCKED, stockLockedDto)
                    skuStocked = true
                    break
                }
            }
            //当前商品的所有仓库都没锁住
            if (!skuStocked) {
                throw NoStockException(skuWareHasStock.skuId)
            }
        }
        return true
    }

    data class SkuWareHasStock(
        var skuId: Long = 0,
        var num: Int = 0,
        var wareIds: List<Long> = emptyList(),
    )

    /**
     * 处理库存解锁的消息
     *
     * 1. 接受到消息，需要先查看 wms_ware_order_task_detail 表中是否有数据
     * - 无：说明下单时，虽然发送了消息，但是最后可能因为有某个货物无库存等情况，进行了整体回滚。所以这种情况无需处理
     * - 有：说明库存成功，至于下订单的其它业务是否成功，还未知
     *      - 没有这个订单: 必须解锁
     *      - 有这个订单: 查看订单状态
     *           - 已取消：解锁库存
     *           - 没取消：不能解锁
     */
    override fun unlockStock(stockLockedDto: StockLockedDto) {
        //查询库中有无指定的 '库存工作单详情'，如果无则无需解锁
        val wareOrderTaskDetailEntity = wareOrderTaskDetailService.getById(stockLockedDto.stockLockedDetail.id)
        if (wareOrderTaskDetailEntity != null) {
            // 根据'库存工作单'查询订单的订单号信息
            val wareOrderTaskEntity = wareOrderTaskService.getById(stockLockedDto.id)
            // 根据订单号信息 远程查询订单
            val r = orderFeignService.getOrderStatus(wareOrderTaskEntity.orderSn!!)
            if (r.isSuccess()) {
                //订单数据返回成功
                val orderVo = r.getData(object : TypeReference<OrderVo>() {})
                //订单不存在 或是已关闭 就进行解锁
                if (orderVo == null || orderVo.status == OrderStatus.ORDER_STATUS_CLOSED) {
                    if (wareOrderTaskDetailEntity.lockStatus == WareLockStatus.WARE_STATUS_LOCKED) {
                        stockLockedDto.stockLockedDetail.let {
                            unLockStock(it.skuId!!, it.wareId!!, it.skuNum!!, stockLockedDto.id)
                        }
                    }
                }
            } else {
                throw RuntimeException("远程查询失败")
            }
        }//else 无需解锁
    }

    private fun unLockStock(skuId: Long, wareId: Long, skuNum: Int, detailId: Long) {
        this.baseMapper.unLockStock(skuId, wareId, skuNum)

        KtUpdateChainWrapper(WareOrderTaskDetailEntity::class.java)
            .set(WareOrderTaskDetailEntity::lockStatus, WareLockStatus.WARE_STATUS_UNLOCK)
            .eq(WareOrderTaskDetailEntity::id, detailId)
            .update()
    }
}