package com.vurtnewk.mall.ware.config

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
        return TopicExchange("stock-event-exchange", true, false)
    }

    @Bean
    fun stockReleaseStockQueue(): Queue {
        return Queue("stock.release.stock.queue", true, false, false)
    }

    @Bean
    fun stockDelayQueue(): Queue {
        val arguments = mapOf(
            "x-dead-letter-exchange" to "stock-event-exchange",
            "x-dead-letter-routing-key" to "stock.release",
            "x-message-ttl" to 120000
        )
        return Queue("stock.delay.queue", true, false, false, arguments)
    }

    @Bean
    fun stockReleaseBinding(): Binding {
        return Binding(
            "stock.release.stock.queue", Binding.DestinationType.QUEUE,
            "stock-event-exchange", "stock.release.#", null
        )
    }

    @Bean
    fun stockLockedBinding(): Binding {
        return Binding(
            "stock.delay.queue", Binding.DestinationType.QUEUE,
            "stock-event-exchange", "stock.locked", null
        )
    }

    /**
     * 这个只是为了测试创建，
     * 因为没有监听时，运行项目 不会自动创建上面的 交换机、队列等
     * 只有进行监听了，发现没有对应的 交换机、队列等 才会创建
     */
    @RabbitListener(queues = ["stock.release.stock.queue"])
    fun handle(message: Message){
        println("message => $message")
    }
}