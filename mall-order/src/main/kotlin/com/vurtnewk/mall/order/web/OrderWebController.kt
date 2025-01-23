package com.vurtnewk.mall.order.web

import com.vurtnewk.mall.order.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 19:34
 */
@Controller
class OrderWebController(
    private val orderService: OrderService
) {


    @GetMapping("/toTrade")
    suspend  fun toTrade(model: Model): String {
        val orderConfirmVo = orderService.confirmOrder()
        model.addAttribute("orderConfirmData",orderConfirmVo)
        //展示订单
        return "confirm"
    }
}