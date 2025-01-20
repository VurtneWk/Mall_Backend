package com.vurtnewk.mall.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class MallSearchApplication

fun main(args: Array<String>) {
	runApplication<MallSearchApplication>(*args)
}