package com.vurtnewk.mall.product

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession
@MapperScan("com.vurtnewk.mall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = ["com.vurtnewk.mall.product.feign"])
class MallProductApplication

fun main(args: Array<String>) {
    runApplication<MallProductApplication>(*args)
}
