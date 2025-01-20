package com.vurtnewk.mall.search.controller

import com.vurtnewk.common.dto.SkuEsModelDto
import com.vurtnewk.common.excetion.BizCodeEnum
import com.vurtnewk.common.utils.R2
import com.vurtnewk.mall.search.service.ProductSaveService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author   vurtnewk
 * @since    2025/1/15 21:04
 */

@RequestMapping("/search")
@RestController
class ElasticSaveController(
    private val mProductSaveService: ProductSaveService,
) {

    /**
     * 上架商品
     */
    @PostMapping("/product")
    fun productStatusUp(@RequestBody skuEsModels: List<SkuEsModelDto>): R2<Any> {
        val b = mProductSaveService.productStatusUp(skuEsModels)
        if (b) {
            return R2.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.code, BizCodeEnum.PRODUCT_UP_EXCEPTION.msg)
        }
        return R2.ok()
    }
}