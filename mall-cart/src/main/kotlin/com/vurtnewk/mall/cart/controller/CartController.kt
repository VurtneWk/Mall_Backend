package com.vurtnewk.mall.cart.controller

import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.cart.interceptor.CartInterceptor
import com.vurtnewk.mall.cart.service.CartService
import com.vurtnewk.mall.cart.vo.CartItem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 11:59
 */
@Controller
class CartController(
    private val cartService: CartService,
) {

    /**
     * cookie 里面有一个 user-key (一个月后过期的) 标识用户。
     *
     *
     * 登录: session 有
     * 未登录: 按照 cookie 里面带来的 user-key 来做
     *
     */
    @GetMapping("/cart.html")
    fun cartListPage(model: Model): String {
//        val userInfo = CartInterceptor.threadLocal.get()
//        logInfo("userInfo ==> $userInfo")
        val cart = cartService.getCart()
        model.addAttribute("cart", cart)
        return "cartList"
    }

    /**
     * 添加商品到购物车
     */
    @GetMapping("/addToCart")
    fun addToCart(
        @RequestParam("skuId") skuId: Long,
        @RequestParam("num") num: Int,
        redirectAttributes: RedirectAttributes,
    ): String {
        cartService.addToCart(skuId, num)
        redirectAttributes.addAttribute("skuId", skuId)
        return "redirect:http://cart.mall.com/addToCartSuccess.html"
    }

    /**
     * 这是避免 在成功页面 刷新时 再次进行添加商品到购物车
     */
    @GetMapping("/addToCartSuccess.html")
    fun addToCartSuccessPage(@RequestParam("skuId") skuId: Long, model: Model): String {
        //重定向到成功页面之后. 再次查询购物车数据
        val cartItem = cartService.getCartItem(skuId)
        model.addAttribute("item", cartItem)
        return "success"
    }

    /**
     * 改变选中状态
     */
    @GetMapping("/checkItem")
    fun checkItem(@RequestParam("skuId") skuId: Long, @RequestParam("checked") checked: Int): String {
        cartService.checkItem(skuId, checked)
        return "redirect:http://cart.mall.com/cart.html"
    }

    @GetMapping("/countItem")
    fun changeItemCount(@RequestParam("skuId") skuId: Long, @RequestParam("num") num: Int): String {
        cartService.changeItemCount(skuId, num)
        return "redirect:http://cart.mall.com/cart.html"
    }

    /**
     * 删除某个购物项
     */
    @GetMapping("/deleteItem")
    fun deleteItem(@RequestParam("skuId") skuId: Long): String {
        cartService.deleteItem(skuId)
        return "redirect:http://cart.mall.com/cart.html"
    }


    @GetMapping("/currentUserCartItems")
    @ResponseBody
    fun getCurrentUserCartItems(): List<CartItem> {
        println("getCurrentUserCartItems")
        return cartService.getCurrentUserCartItems()
    }
}