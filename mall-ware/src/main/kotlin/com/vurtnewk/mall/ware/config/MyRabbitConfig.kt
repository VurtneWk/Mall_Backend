package com.vurtnewk.mall.ware.config

import com.vurtnewk.mall.ware.constants.MQConstants
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 14:52
 */
@Configuration
class MyRabbitConfig {

    /**
     * 注入一个 Jackson2JsonMessageConverter 来代替默认的序列化转换器
     */
    @Bean
    fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun stockEventExchange(): Exchange {
        return TopicExchange(MQConstants.Exchange.STOCK_EVENT, true, false)
    }

    @Bean
    fun stockReleaseStockQueue(): Queue {
        return Queue(MQConstants.Queue.STOCK_RELEASE_STOCK, true, false, false)
    }

    @Bean
    fun stockDelayQueue(): Queue {
        val arguments = mapOf(
            "x-dead-letter-exchange" to MQConstants.Exchange.STOCK_EVENT,
            "x-dead-letter-routing-key" to MQConstants.RoutingKey.STOCK_RELEASE,
            "x-message-ttl" to 120000
        )
        return Queue(MQConstants.Queue.STOCK_DELAY, true, false, false, arguments)
    }

    @Bean
    fun stockReleaseBinding(): Binding {
        return Binding(
            MQConstants.Queue.STOCK_RELEASE_STOCK, Binding.DestinationType.QUEUE,
            MQConstants.Exchange.STOCK_EVENT, MQConstants.RoutingKey.STOCK_RELEASE_WILDCARD, null
        )
    }

    @Bean
    fun stockLockedBinding(): Binding {
        return Binding(
            MQConstants.Queue.STOCK_DELAY, Binding.DestinationType.QUEUE,
            MQConstants.Exchange.STOCK_EVENT, MQConstants.RoutingKey.STOCK_LOCKED, null
        )
    }

    /**
     * 这个只是为了测试创建，
     * 因为没有监听时，运行项目 不会自动创建上面的 交换机、队列等
     * 只有进行监听了，发现没有对应的 交换机、队列等 才会创建
     */
    @RabbitListener(queues = ["stock.release.stock.queue"])
    fun handle(message: Message) {
        println("message => $message")
    }
}