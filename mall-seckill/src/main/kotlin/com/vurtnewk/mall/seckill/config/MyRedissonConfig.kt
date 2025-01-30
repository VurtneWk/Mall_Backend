package com.vurtnewk.mall.seckill.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * redisson配置文件
 * @author   vurtnewk
 * @since    2025/1/17 16:42
 */
@Configuration
class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    fun redisson(): RedissonClient {
        val config = Config()
            .apply {
                //实际单机模式 默认就是 使用本机地址
                useSingleServer().address = "redis://127.0.0.1:6379"
            }
        return Redisson.create(config)
    }
}