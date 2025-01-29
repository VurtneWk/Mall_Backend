package com.vurtnewk.mall.order.config

import com.vurtnewk.common.constants.MQConstants
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
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
//    @RabbitListener(queues = ["order.release.order.queue"])
//    fun listener(orderEntity: OrderEntity, channel: Channel, message: Message) {
//        println("收到过期的订单信息：=> $orderEntity")
//        channel.basicAck(message.messageProperties.deliveryTag, false)
//    }

    /**
     * 容器中的 binding、queue、exchange 都会自动创建
     */

    /**
     * 延时队列
     */
    @Bean
    fun orderDelayQueue(): Queue {
        val arguments = mapOf(
            "x-dead-letter-exchange" to MQConstants.Order.Exchange.ORDER_EVENT_EXCHANGE,
            "x-dead-letter-routing-key" to MQConstants.Order.RoutingKey.ORDER_RELEASE_ORDER,
            "x-message-ttl" to 60000
        )
        return Queue(MQConstants.Order.Queue.ORDER_DELAY_QUEUE, true, false, false, arguments)
    }

    /**
     * 真正要处理订单创建30分钟后的队列
     */
    @Bean
    fun orderReleaseQueue(): Queue {
        return Queue(MQConstants.Order.Queue.ORDER_RELEASE_ORDER_QUEUE, true, false, false)
    }

    /**
     * 交换机
     */
    @Bean
    fun orderEventExchange(): Exchange {
        return TopicExchange(MQConstants.Order.Exchange.ORDER_EVENT_EXCHANGE, true, false)
    }

    /**
     * 绑定延时队列
     */
    @Bean
    fun orderCreateOrderBinding(): Binding {
        return Binding(
            MQConstants.Order.Queue.ORDER_DELAY_QUEUE, Binding.DestinationType.QUEUE,
            MQConstants.Order.Exchange.ORDER_EVENT_EXCHANGE, MQConstants.Order.RoutingKey.ORDER_CREATE_ORDER, null
        )
    }

    /**
     * 绑定消费订单创建的队列
     */
    @Bean
    fun orderReleaseOrderBinding(): Binding {
        return Binding(
            MQConstants.Order.Queue.ORDER_RELEASE_ORDER_QUEUE, Binding.DestinationType.QUEUE,
            MQConstants.Order.Exchange.ORDER_EVENT_EXCHANGE, MQConstants.Order.RoutingKey.ORDER_RELEASE_ORDER, null
        )
    }

    /**
     * 订单关闭和库存释放 直接绑定
     */
    @Bean
    fun orderReleaseOtherBinding(): Binding {
        return Binding(
            MQConstants.Ware.Queue.STOCK_RELEASE_STOCK, Binding.DestinationType.QUEUE,
            MQConstants.Order.Exchange.ORDER_EVENT_EXCHANGE, MQConstants.Order.RoutingKey.ORDER_RELEASE_OTHER_WILDCARD, null
        )
    }


}