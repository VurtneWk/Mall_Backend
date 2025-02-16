package com.vurtnewk.mall.search.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

/**
 *
 * @author   vurtnewk
 * @since    2025/1/19 01:53
 */
@FeignClient("mall-product")
interface ProductFeignService {


    @GetMapping("/product/attr/info/{attrId}")
    fun getAttrsInfo(@PathVariable("attrId") attrId: Long): R

    @GetMapping("/product/brand/infos")
    fun getBrandsInfo(@RequestParam("brandIds") brandIds: List<Long>): R
}