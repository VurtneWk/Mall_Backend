package com.vurtnewk.mall.product.web

import com.vurtnewk.mall.product.service.CategoryService
import com.vurtnewk.mall.product.vo.Catalog2Vo
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * 首页
 * @author   vurtnewk
 * @since    2025/1/16 01:42
 */
@Controller
class IndexController(
    private val categoryService: CategoryService,
) {

    @GetMapping(value = ["/", "/index.html"])
    fun indexPage(model: Model): String {
        // 查出所有的1级分类
        val categoryEntities = categoryService.getTopLevelCategoryList()
        model.addAttribute("categories", categoryEntities)
        return "index"//已经有了默认前缀和后缀
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    fun getCatalogJson(): Map<String, List<Catalog2Vo>> {
        return categoryService.getCatalogJson()
    }

    @ResponseBody
    @GetMapping("/hello")
    fun hello():String{
        return "hello"
    }
}