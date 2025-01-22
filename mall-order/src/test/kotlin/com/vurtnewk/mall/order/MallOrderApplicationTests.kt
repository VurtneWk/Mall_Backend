package com.vurtnewk.mall.order

import org.junit.jupiter.api.Test
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MallOrderApplicationTests(
    private val amqpAdmin: AmqpAdmin
) {


    /**
     * 1. 如何创建 exchange 、 queue 、 binding
     * 2. 如何收发消息
     */
    fun testRabbit() {

    }


    @Test
    fun contextLoads() {
    }

}
