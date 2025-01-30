package com.vurtnewk.mall.member.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 17:57
 */
@FeignClient("mall-order")
interface OrderFeignService {
    @PostMapping("/order/order/listWithItem")
    fun listWithItem(@RequestBody params: Map<String, Any>): R
}