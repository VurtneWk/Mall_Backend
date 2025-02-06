package com.vurtnewk.mall.product.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author   vurtnewk
 * @since    2025/2/6 23:40
 */
@FeignClient("mall-seckill")
interface SecKillFeignService {

    /**
     * 查询指定 skuId 的秒杀信息
     */
    @GetMapping("/sku/secKill/{skuId}")
    fun getSkuSecKillInfo(@PathVariable("skuId") skuId: Long): R
}