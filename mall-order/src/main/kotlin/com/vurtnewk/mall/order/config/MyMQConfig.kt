package com.vurtnewk.mall.order.config

import com.rabbitmq.client.Channel
import com.vurtnewk.mall.order.entity.OrderEntity
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * rabbitmq 配置
 * @author   vurtnewk
 * @since    2025/1/28 01:05
 */
@Configuration
class MyMQConfig {


    /**
     * 测试消息
     */
    @RabbitListener(queues = ["order.release.order.queue"])
    fun listener(orderEntity: OrderEntity, channel: Channel, message: Message) {
        println("收到过期的订单信息：=> $orderEntity")
        channel.basicAck(message.messageProperties.deliveryTag, false)
    }

    /**
     * 容器中的 binding、queue、exchange 都会自动创建
     */

    /**
     * 延时队列
     */
    @Bean
    fun orderDelayQueue(): Queue {
        val arguments = mapOf(
            "x-dead-letter-exchange" to "order-event-exchange",
            "x-dead-letter-routing-key" to "order.release.order",
            "x-message-ttl" to 60000
        )
        return Queue("order.delay.queue", true, false, false, arguments)
    }

    /**
     * 真正要处理订单创建30分钟后的队列
     */
    @Bean
    fun orderReleaseQueue(): Queue {
        return Queue("order.release.order.queue", true, false, false)
    }

    /**
     * 交换机
     */
    @Bean
    fun orderEventExchange(): Exchange {
        return TopicExchange("order-event-exchange", true, false)
    }

    /**
     * 绑定延时队列
     */
    @Bean
    fun orderCreateOrderBinding(): Binding {
        return Binding(
            "order.delay.queue", Binding.DestinationType.QUEUE,
            "order-event-exchange", "order.create.order", null
        )
    }

    /**
     * 绑定消费订单创建的队列
     */
    @Bean
    fun orderReleaseOrderBinding(): Binding {
        return Binding(
            "order.release.order.queue", Binding.DestinationType.QUEUE,
            "order-event-exchange", "order.release.order", null
        )
    }

}