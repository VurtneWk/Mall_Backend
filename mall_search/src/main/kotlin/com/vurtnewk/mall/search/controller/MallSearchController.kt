package com.vurtnewk.mall.search.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * 商品搜索页面controller
 * @author   vurtnewk
 * @since    2025/1/18 02:19
 */
@Controller
class MallSearchController {


    @GetMapping(value = ["/","/list.html"])
    fun listPage(): String {
        return "list"
    }

}