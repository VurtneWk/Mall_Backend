package com.vurtnewk.mall.ware.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/13 21:38
 */
@FeignClient("mall-product")
interface ProductFeignService {
    /**
     * 1. /product/skuinfo/info/{skuId} 直接让后台指定服务处理
     * 2. /api/product/skuinfo/info/{skuId} 给mall-gateway 发请求
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    fun info(@PathVariable("skuId") skuId: Long): R
}