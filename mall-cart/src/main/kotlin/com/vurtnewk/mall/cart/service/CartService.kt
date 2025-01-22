package com.vurtnewk.mall.cart.service

import com.vurtnewk.mall.cart.vo.Cart
import com.vurtnewk.mall.cart.vo.CartItem

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 11:56
 */
interface CartService {
    fun addToCart(skuId: Long, num: Int): CartItem
    fun getCartItem(skuId: Long): CartItem?
    fun getCart(): Cart
    fun clearCart(cartKey: String)
}