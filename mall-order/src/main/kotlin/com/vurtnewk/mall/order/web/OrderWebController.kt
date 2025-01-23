package com.vurtnewk.mall.order.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 19:34
 */
@Controller
class OrderWebController {


    @GetMapping("/toTrade")
    fun toTrade(): String {
        return "confirm"
    }
}