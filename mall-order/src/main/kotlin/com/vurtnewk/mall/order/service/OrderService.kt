package com.vurtnewk.mall.order.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.vo.OrderConfirmVo
import com.vurtnewk.mall.order.vo.OrderSubmitVo
import com.vurtnewk.mall.order.vo.SubmitOrderResponseVo

/**
 * 订单
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:21
 */
interface OrderService : IService<OrderEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils
    suspend fun confirmOrder(): OrderConfirmVo
    fun submitOrder(orderSubmitVo: OrderSubmitVo): SubmitOrderResponseVo
}

