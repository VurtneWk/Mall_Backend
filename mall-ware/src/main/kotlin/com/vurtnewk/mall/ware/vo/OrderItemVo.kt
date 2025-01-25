package com.vurtnewk.mall.ware.vo

import java.math.BigDecimal

/**
 *
 * @author   vurtnewk
 * @since    2025/1/25 07:33
 */
data class OrderItemVo(
    var skuId: Long = 0L,
    var title: String? = null,
    var image: String? = null,
    var skuAttr: List<String> = emptyList(),
    var price: BigDecimal = BigDecimal.ZERO,
    var count: Int = 0,
    var weight: BigDecimal = BigDecimal.ZERO,
) {
    val totalPrice: BigDecimal
        get() {
            return this.price.multiply(BigDecimal(this.count))
        }
}
