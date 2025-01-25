package com.vurtnewk.mall.ware.vo

/**
 * 锁定库存结果
 * @author   vurtnewk
 * @since    2025/1/25 07:35
 */
data class LockStockResultDto(
    var skuId: Long,
    var num: Int,
    var locked: Boolean,
)
