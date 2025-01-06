package com.vurtnewk.amll.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * 开启服务注册、发现
 */
@EnableDiscoveryClient
//排除数据库相关模块
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
//@SpringBootApplication
class MallGatewayApplication

fun main(args: Array<String>) {
	runApplication<MallGatewayApplication>(*args)
}
