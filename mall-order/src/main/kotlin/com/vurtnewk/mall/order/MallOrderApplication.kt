package com.vurtnewk.mall.order

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableRabbit
@EnableDiscoveryClient
@SpringBootApplication
class MallOrderApplication

fun main(args: Array<String>) {
    runApplication<MallOrderApplication>(*args)
}
