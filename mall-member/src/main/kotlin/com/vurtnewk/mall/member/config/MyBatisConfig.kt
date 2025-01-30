package com.vurtnewk.mall.member.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * 分页插件
 * @author   vurtnewk
 * @since    2025/1/8 23:30
 */
@Configuration
@EnableTransactionManagement //开启事务
@MapperScan("com.vurtnewk.mall.member.dao")
class MyBatisConfig {

    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        return MybatisPlusInterceptor().apply {
            addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL))
        }
    }
}