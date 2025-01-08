package com.vurtnewk.mall.third_party

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class MallThirdPartyApplication

fun main(args: Array<String>) {
	runApplication<MallThirdPartyApplication>(*args)
}
