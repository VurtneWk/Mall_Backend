package com.vurtnewk.mall.cart.vo

import java.math.BigDecimal

/**
 * 购物车
 * @author   vurtnewk
 * @since    2025/1/22 11:01
 */
data class Cart(
    var items: List<CartItem> = emptyList(),
    /**
     * 减免价格
     */
    var reduce: BigDecimal = BigDecimal(0.00),
) {
    /**
     * 商品数量
     */
    val countNum: Int
        get() {
            return items.sumOf { it.count }
        }

    /**
     * 商品类型数量
     */
    val countType: Int
        get() {
            return items.size
        }

    /**
     * 商品总价
     */
    val totalAmount: BigDecimal
        get() {
            return items.sumOf { (if (it.check) it.totalPrice else BigDecimal.ZERO) }
        }
}

data class CartItem(
    var skuId: Long = 0L,
    var title: String? = null,
    var image: String? = null,
    var skuAttr: List<String> = emptyList(),
    var check: Boolean = true,
    var price: BigDecimal = BigDecimal.ZERO,
    var count: Int = 0,
) {
    val totalPrice: BigDecimal
        get() {
            return this.price.multiply(BigDecimal(this.count))
        }
}
