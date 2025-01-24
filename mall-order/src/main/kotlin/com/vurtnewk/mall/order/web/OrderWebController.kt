package com.vurtnewk.mall.order.web

import com.vurtnewk.mall.order.service.OrderService
import com.vurtnewk.mall.order.vo.OrderSubmitVo
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 19:34
 */
@Controller
class OrderWebController(
    private val orderService: OrderService,
) {


    @GetMapping("/toTrade")
    suspend fun toTrade(model: Model): String {
        val orderConfirmVo = orderService.confirmOrder()
        model.addAttribute("orderConfirmData", orderConfirmVo)
        //展示订单
        return "confirm"
    }

    /**
     *  # 提交订单
     *  下单 => 去创建订单 => 验令牌 => 验价格 => 验货存
     *
     *  下单成功 来到支付选择页
     *
     *  下单失败 回到订单确认页重新确认订单信息
     */
    @PostMapping("/submitOrder")
    @ResponseBody
    suspend fun submitOrder(orderSubmitVo: OrderSubmitVo) {
        println("订单提交的数据 orderService = $orderSubmitVo")
//        return ""
    }
}