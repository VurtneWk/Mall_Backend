package com.vurtnewk.mall.order.vo

/**
 * Sku对应的是否有库存
 * @author   vurtnewk
 * @since    2025/1/15 20:01
 */
data class SkuHasStockVo(
    var skuId: Long = 0L,
    var hasStock: Boolean = false,
)
