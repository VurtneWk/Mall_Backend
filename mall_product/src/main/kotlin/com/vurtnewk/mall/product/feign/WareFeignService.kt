package com.vurtnewk.mall.product.feign

import com.vurtnewk.common.utils.R2
import com.vurtnewk.mall.product.vo.SkuHasStockVo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * 库存系统远程服务
 * @author   vurtnewk
 * @since    2025/1/15 19:58
 */
@FeignClient("mall-ware")
interface WareFeignService {

    /**
     * 1. R加泛型
     * 2. 直接返回需要的数据
     * 3. 自己封装解析结果
     */
    @PostMapping("/ware/waresku/hasStock")
    fun getSkusHasStock(@RequestBody skuIds: List<Long>): R2<List<SkuHasStockVo>>
}