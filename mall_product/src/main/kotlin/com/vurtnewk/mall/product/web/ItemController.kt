package com.vurtnewk.mall.product.web

import com.vurtnewk.mall.product.service.SkuInfoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 * 商品详情
 * @author   vurtnewk
 * @since    2025/1/19 17:29
 */
@Controller
class ItemController(
    private val mSkuInfoService: SkuInfoService,
) {


    @GetMapping("/{skuId}.html")
    fun skuItem(@PathVariable("skuId") skuId: Long): String {
        println("查询--> $skuId")
        val skuItemVo = mSkuInfoService.queryItem(skuId)
        return "item"
    }
}