package com.vurtnewk.mall.member.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 16:54
 */
@Controller
class MemberWebController {


    @GetMapping("/memberOrder.html")
    fun memberOrderPage(): String {
        return "orderList"
    }
}