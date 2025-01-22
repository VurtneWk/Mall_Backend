package com.vurtnewk.mall.cart.controller

import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.cart.interceptor.CartInterceptor
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 11:59
 */
@Controller
class CartController {

    /**
     * cookie 里面有一个 user-key (一个月后过期的) 标识用户。
     *
     *
     * 登录: session 有
     * 未登录: 按照 cookie 里面带来的 user-key 来做
     *
     */
    @GetMapping("/cart.html")
    fun cartListPage(): String {
        val userInfo = CartInterceptor.threadLocal.get()
        logInfo("userInfo ==> $userInfo")
        return "cartList"
    }

}