package com.vurtnewk.mall.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession
@EnableFeignClients(basePackages = ["com.vurtnewk.mall.member.feign"]) //开启远程调用功能
@EnableDiscoveryClient
@SpringBootApplication
class MallMemberApplication

fun main(args: Array<String>) {
    runApplication<MallMemberApplication>(*args)
}
