package com.vurtnewk.mall.search.controller

import com.vurtnewk.mall.search.service.MallSearchService
import com.vurtnewk.mall.search.vo.SearchParam
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

/**
 * 商品搜索页面controller
 * @author   vurtnewk
 * @since    2025/1/18 02:19
 */
@Controller
class MallSearchController(
    private val mMallSearchService: MallSearchService,
) {


    @GetMapping(value = ["/", "/list.html"])
    fun listPage(param: SearchParam, model: Model): String {
        val searchResult = mMallSearchService.search(param)
        model.addAttribute("result", searchResult)
        return "list"
    }

}