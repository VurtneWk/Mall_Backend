package com.vurtnewk.mall.cart.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 14:17
 */
@FeignClient("mall-product")
interface ProductFeignService {

    @RequestMapping("/product/skuinfo/info/{skuId}")
    fun getSkuInfo(@PathVariable("skuId") skuId: Long): R

    @GetMapping("/product/skusaleattrvalue/stringList/{skuId}")
    fun getSkuSaleAttrValues(@PathVariable("skuId") skuId: Long): List<String>
}