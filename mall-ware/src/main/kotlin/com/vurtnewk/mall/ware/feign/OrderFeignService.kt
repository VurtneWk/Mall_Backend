package com.vurtnewk.mall.ware.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author   vurtnewk
 * @since    2025/1/28 23:01
 */
@FeignClient("mall-order")
interface OrderFeignService {

    @GetMapping("/order/order/status/{orderSn}")
    fun getOrderStatus(@PathVariable("orderSn") orderSn: String): R
}