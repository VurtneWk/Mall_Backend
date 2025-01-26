package com.vurtnewk.mall.order.web

import com.vurtnewk.common.excetion.NoStockException
import com.vurtnewk.mall.order.service.OrderService
import com.vurtnewk.mall.order.vo.OrderSubmitVo
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes


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
    fun submitOrder(
        orderSubmitVo: OrderSubmitVo,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): String {
        try {
            val responseVo =    orderService.submitOrder(orderSubmitVo)
            if (responseVo.code == 0) {
                //下单成功
                model.addAttribute("submitOrderResp", responseVo)
                return "pay"
            } else {
                val msg = when (responseVo.code) {
                    1 -> "下单失败: 请刷新后再次提交"
                    2 -> "下单失败: 订单商品价格发生变化，请确认后在次提交"
                    3 -> "下单失败: 库存锁定失败，商品库存不足"
                    else -> "下单失败:"
                }
                redirectAttributes.addFlashAttribute("msg", msg)
                return "redirect:http://order.mall.com/toTrade"
            }
        } catch (e: Exception) {
            if (e is NoStockException) {
                val message = e.message
                redirectAttributes.addFlashAttribute("msg", message)
            }
            return "redirect:http://order.mall.com/toTrade"
        }
    }
}