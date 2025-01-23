package com.vurtnewk.mall.order.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 18:23
 */
@Controller
class HelloController {

    @GetMapping("/{page}.html")
    fun listPage(@PathVariable("page") page: String): String {
        return page
    }
}