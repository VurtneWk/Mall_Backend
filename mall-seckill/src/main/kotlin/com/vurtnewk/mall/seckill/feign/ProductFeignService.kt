package com.vurtnewk.mall.seckill.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 01:53
 */
@FeignClient("mall-product")
interface ProductFeignService {

    @RequestMapping("/product/skuinfo/info/{skuId}")
    fun getSkuInfo(@PathVariable("skuId") skuId: Long): R
}