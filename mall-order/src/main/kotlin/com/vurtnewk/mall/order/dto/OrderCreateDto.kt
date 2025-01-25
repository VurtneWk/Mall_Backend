package com.vurtnewk.mall.order.dto

import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.entity.OrderItemEntity
import java.math.BigDecimal

/**
 * 创建订单时返回的数据
 * @author   vurtnewk
 * @since    2025/1/25 03:49
 */
data class OrderCreateDto(
    var order: OrderEntity = OrderEntity(),
    var orderItems: List<OrderItemEntity> = emptyList(),
    var payPrice: BigDecimal = BigDecimal.ZERO,
    var fare: BigDecimal = BigDecimal.ZERO,
)