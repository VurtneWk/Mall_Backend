package com.vurtnewk.mall.order.feign

import com.vurtnewk.mall.order.vo.OrderItemVo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 20:54
 */
@FeignClient("mall-cart")
interface CartFeignService {

    @GetMapping("/currentUserCartItems")
    fun getCurrentUserCartItems(): List<OrderItemVo>

}