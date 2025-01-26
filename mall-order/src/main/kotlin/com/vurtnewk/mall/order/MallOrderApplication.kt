package com.vurtnewk.mall.order

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
@EnableAspectJAutoProxy
@EnableFeignClients
@EnableRedisHttpSession
@EnableRabbit
@EnableDiscoveryClient
@SpringBootApplication
class MallOrderApplication

fun main(args: Array<String>) {
    runApplication<MallOrderApplication>(*args)
}
