package com.vurtnewk.mall.order.vo

import com.vurtnewk.mall.order.entity.OrderEntity

/**
 *
 * @author   vurtnewk
 * @since    2025/1/25 03:25
 */
data class SubmitOrderResponseVo(
    var code: Int = 0,
    var order: OrderEntity? = null,
)