package com.vurtnewk.mall.order.config

import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 14:52
 */
@Configuration
class MyRabbitConfig {
//( @Lazy private val rabbitTemplate: RabbitTemplate)


//    @Autowired
//    @Lazy
//    lateinit var rabbitTemplate: RabbitTemplate

    /**
     * 注入一个 Jackson2JsonMessageConverter 来代替默认的序列化转换器
     */
    @Bean
    fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    /**
     * 教程使用的这个方式
     * 会导致循环依赖 @Lazy 都没用
     */
//    @PostConstruct //MyRabbitConfig 对象创建完成之后，执行这个方法
//    fun initRabbitTemplate() {
//        /**
//         * - correlationData 当前消息的唯一关联数据 （这个消息的唯一ID）
//         * - ack 消息是否成功收到
//         * - cause 失败的原因
//         */
//        rabbitTemplate.setConfirmCallback { correlationData, ack, cause ->
//            println("correlationData = [${correlationData}], ack = [${ack}], cause = [${cause}]")
//        }
//    }

    @Bean
    @Lazy
    fun customRabbitTemplate(rabbitTemplateConfigurer: RabbitTemplateConfigurer, connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplateConfigurer.configure(rabbitTemplate, connectionFactory)
//        rabbitTemplate.messageConverter = messageConverter()
        /**
         * 服务器收到了消息
         * - correlationData 当前消息的唯一关联数据 （这个消息的唯一ID）
         * - ack 消息是否成功收到 broker 接受到消息就为 true
         * - cause 失败的原因
         */
        rabbitTemplate.setConfirmCallback { correlationData, ack, cause ->
            //
            println("correlationData = [${correlationData}], ack = [${ack}], cause = [${cause}]")
        }
        /**
         *  - message 投递失败的详细信息
         * - replyCode; 回复的状态码
         * - replyText; 回复的本文内容
         * - exchange; 给的哪个交换机
         * - routingKey; 给的哪个 rk
         */
        rabbitTemplate.setReturnsCallback {
            // 报错了，修改数据库，当前消息的状态
            println(it.toString())
        }

        return rabbitTemplate
    }
}