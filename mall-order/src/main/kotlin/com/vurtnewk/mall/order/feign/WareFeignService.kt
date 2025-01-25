package com.vurtnewk.mall.order.feign

import com.vurtnewk.common.utils.R
import com.vurtnewk.common.utils.R2
import com.vurtnewk.mall.order.vo.SkuHasStockVo
import com.vurtnewk.mall.order.vo.WareSkuLockVo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 *
 * @author   vurtnewk
 * @since    2025/1/24 01:21
 */
@FeignClient("mall-ware")
interface WareFeignService {

    @PostMapping("/ware/waresku/hasStock")
    fun getSkusHasStock(@RequestBody skuIds: List<Long>): R2<List<SkuHasStockVo>>

    @RequestMapping("/ware/wareinfo/fare")
    fun getFare(@RequestParam("addrId") addrId: Long): R

    @PostMapping("/ware/waresku/lock/order")
    fun orderLockStock(@RequestBody vo: WareSkuLockVo): R
}