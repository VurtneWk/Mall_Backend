package com.vurtnewk.mall.order.web

import com.vurtnewk.mall.order.config.AlipayTemplate
import com.vurtnewk.mall.order.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 00:42
 */
@Controller
class PayWebController(
    private val alipayTemplate: AlipayTemplate,
    private val orderService: OrderService
) {

    @GetMapping(value = ["/payOrder"], produces = ["text/html"])
    @ResponseBody
    fun payOrder(@RequestParam("orderSn") orderSn: String): String {
        val payVo = orderService.getOrderPay(orderSn)
        return alipayTemplate.pay(payVo) // 返回的是一段html代码
    }
}