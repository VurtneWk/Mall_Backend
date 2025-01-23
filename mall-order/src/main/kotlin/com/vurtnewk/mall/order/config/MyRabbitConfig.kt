package com.vurtnewk.mall.order.config

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
}