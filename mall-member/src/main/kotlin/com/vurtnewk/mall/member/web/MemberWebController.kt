package com.vurtnewk.mall.member.web

import com.vurtnewk.mall.member.feign.OrderFeignService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 16:54
 */
@Controller
class MemberWebController(
    private val orderFeignService: OrderFeignService,
) {

    /**
     * 支付宝的同步回调页面
     */
    @GetMapping("/memberOrder.html")
    fun memberOrderPage(@RequestParam(value = "pageNum", defaultValue = "1") pageNum: Int, model: Model): String {
        val map = mapOf("page" to pageNum)
        val r = orderFeignService.listWithItem(map)
        model.addAttribute("orders", r)
        println("r=> $r")
        return "orderList"
    }
}