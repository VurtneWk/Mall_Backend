package com.vurtnewk.mall.order.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author   vurtnewk
 * @since    2025/1/25 06:25
 */
@FeignClient("mall-product")
interface ProductFeignService {

    @GetMapping("/product/spuinfo/skuId/{skuId}")
    fun getSpuInfoBySkuId(@PathVariable("skuId") skuId: Long): R
}