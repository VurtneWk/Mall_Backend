package com.vurtnewk.mall.order.listener

import com.rabbitmq.client.Channel
import com.vurtnewk.common.constants.MQConstants
import com.vurtnewk.common.dto.mq.SecKillOrderDto
import com.vurtnewk.mall.order.service.OrderService
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

/**
 * 秒杀单监听
 * @author   vurtnewk
 * @since    2025/2/7 11:28
 */
@Component
@RabbitListener(queues = [MQConstants.Order.Queue.ORDER_SECKILL_ORDER_QUEUE])
class OrderSecKillListener(private val orderService: OrderService) {

    @RabbitHandler
    fun orderSecKillListener(secKillOrderDto: SecKillOrderDto, channel: Channel, message: Message) {
        println("收到秒杀订单创建信息：=> $secKillOrderDto")
        runCatching {
            orderService.createSecKillOrder(secKillOrderDto)
        }.onFailure {
            channel.basicReject(message.messageProperties.deliveryTag, true)
        }.onSuccess {
            channel.basicAck(message.messageProperties.deliveryTag, false)
        }
    }
}