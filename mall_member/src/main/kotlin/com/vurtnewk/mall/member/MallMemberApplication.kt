package com.vurtnewk.mall.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class MallMemberApplication

fun main(args: Array<String>) {
    runApplication<MallMemberApplication>(*args)
}
