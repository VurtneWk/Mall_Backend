package com.vurtnewk.mall.seckill

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class MallSeckillApplication

fun main(args: Array<String>) {
	runApplication<MallSeckillApplication>(*args)
}
