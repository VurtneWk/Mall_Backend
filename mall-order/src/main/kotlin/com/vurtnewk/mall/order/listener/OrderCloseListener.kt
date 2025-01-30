package com.vurtnewk.mall.order.listener

import com.rabbitmq.client.Channel
import com.vurtnewk.common.constants.MQConstants
import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.service.OrderService
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

/**
 * 订单关闭监听
 * @author   vurtnewk
 * @since    2025/1/29 19:11
 */
@Component
@RabbitListener(queues = [MQConstants.Order.Queue.ORDER_RELEASE_ORDER_QUEUE])
class OrderCloseListener(private val orderService: OrderService) {

    @RabbitHandler
    fun orderCloseListener(orderEntity: OrderEntity, channel: Channel, message: Message) {
        println("收到订单关闭的订单信息：=> ${orderEntity.orderSn}")
        runCatching {
            orderService.closeOrder(orderEntity)
            //todo 手动调用支付宝收单

        }.onFailure {
            channel.basicReject(message.messageProperties.deliveryTag, true)
        }.onSuccess {
            channel.basicAck(message.messageProperties.deliveryTag, false)
        }
    }

}