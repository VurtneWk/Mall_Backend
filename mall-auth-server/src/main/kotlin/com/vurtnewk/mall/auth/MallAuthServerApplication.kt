package com.vurtnewk.mall.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class MallAuthServerApplication

fun main(args: Array<String>) {
	runApplication<MallAuthServerApplication>(*args)
}
