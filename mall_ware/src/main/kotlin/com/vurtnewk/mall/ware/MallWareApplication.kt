package com.vurtnewk.mall.ware

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class MallWareApplication

fun main(args: Array<String>) {
    runApplication<MallWareApplication>(*args)
}
