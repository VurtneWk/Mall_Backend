package com.vurtnewk.mall.order.vo

/**
 * 锁定库存时的vo
 * @author   vurtnewk
 * @since    2025/1/25 07:30
 */
data class WareSkuLockVo(
    var orderSn: String = "",
    var locks: List<OrderItemVo> = emptyList(),
)