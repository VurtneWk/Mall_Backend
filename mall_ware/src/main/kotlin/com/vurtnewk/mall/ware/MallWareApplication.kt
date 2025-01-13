package com.vurtnewk.mall.ware

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = ["com.vurtnewk.common","com.vurtnewk.mall.ware"])
class MallWareApplication

fun main(args: Array<String>) {
    runApplication<MallWareApplication>(*args)
}
