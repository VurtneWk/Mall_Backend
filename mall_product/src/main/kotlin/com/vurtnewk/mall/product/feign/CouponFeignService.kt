package com.vurtnewk.mall.product.feign

import com.vurtnewk.common.dto.SkuReductionDto
import com.vurtnewk.common.dto.SpuBoundsDto
import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * 远程服务
 * @author   vurtnewk
 * @since    2025/1/12 17:31
 */
@FeignClient("mall-coupon")
interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    fun saveSpuBounds(@RequestBody spuBoundsDto: SpuBoundsDto): R

    @PostMapping("/coupon/skufullreduction/saveInfo")
    fun saveSkuReduction(@RequestBody  skuReductionDto: SkuReductionDto) :R

}