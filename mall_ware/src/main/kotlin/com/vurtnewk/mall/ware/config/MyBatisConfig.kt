package com.vurtnewk.mall.ware.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * MyBatisConfig
 * @author   vurtnewk
 * @since    2025/1/13 01:42
 */
@Configuration
@EnableTransactionManagement //开启事务
@MapperScan("com.vurtnewk.mall.ware.dao")
class MyBatisConfig {

    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        return MybatisPlusInterceptor().apply {
            addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL))
        }
    }
}