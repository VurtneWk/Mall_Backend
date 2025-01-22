package com.vurtnewk.mall.cart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
class MallCartApplication

fun main(args: Array<String>) {
	runApplication<MallCartApplication>(*args)
}
