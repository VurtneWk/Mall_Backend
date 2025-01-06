package com.vurtnewk.mall.coupon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class MallCouponApplication

fun main(args: Array<String>) {
    runApplication<MallCouponApplication>(*args)
}
