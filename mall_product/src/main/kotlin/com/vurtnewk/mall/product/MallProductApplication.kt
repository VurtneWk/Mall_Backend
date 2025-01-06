package com.vurtnewk.mall.product

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@MapperScan("com.vurtnewk.mall.product.dao")
@SpringBootApplication
class MallProductApplication

fun main(args: Array<String>) {
    runApplication<MallProductApplication>(*args)
}
