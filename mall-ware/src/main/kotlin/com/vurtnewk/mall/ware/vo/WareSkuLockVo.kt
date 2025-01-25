package com.vurtnewk.mall.ware.vo

/**
 * 锁定库存时的vo
 * @author   vurtnewk
 * @since    2025/1/25 07:30
 */
data class WareSkuLockVo(
    val orderSn: String,
    val locks: List<OrderItemVo>,
)