package com.vurtnewk.mall.product.feign

import com.vurtnewk.common.dto.SkuEsModelDto
import com.vurtnewk.common.utils.R2
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * ES服务
 * @author   vurtnewk
 * @since    2025/1/15 22:26
 */
@FeignClient("mall-search")
interface SearchFeignService {

    @PostMapping("/search/product")
    fun productStatusUp(@RequestBody skuEsModels: List<SkuEsModelDto>): R2<Any>
}