package com.vurtnewk.mall.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class MallOrderApplication

fun main(args: Array<String>) {
    runApplication<MallOrderApplication>(*args)
}
