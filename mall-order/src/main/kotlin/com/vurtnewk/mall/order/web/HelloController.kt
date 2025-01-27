package com.vurtnewk.mall.order.web

import com.vurtnewk.mall.order.entity.OrderEntity
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 18:23
 */
@Controller
class HelloController(
    private val rabbitTemplate: RabbitTemplate,
) {

    @GetMapping("/test/createOrder")
    @ResponseBody
    fun createOrderTest(): String {
        val orderEntity = OrderEntity().also {
            it.orderSn = UUID.randomUUID().toString()
            it.createTime = Date()
        }
        rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", orderEntity)
        return "ok"
    }


    @GetMapping("/{page}.html")
    fun listPage(@PathVariable("page") page: String): String {
        return page
    }
}