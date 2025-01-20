package com.vurtnewk.mall.product.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * 构建线程池
 * @author   vurtnewk
 * @since    2025/1/20 10:29
 */
//可以这样注入，也因为 ThreadPoolConfigProperties 在容器中，所以下面直接参数注入
//@EnableConfigurationProperties(ThreadPoolConfigProperties::class)
@Configuration
class MyThreadConfig {

    @Bean
    fun threadPoolExecutor(threadPoolConfigProperties: ThreadPoolConfigProperties): ThreadPoolExecutor {
        return ThreadPoolExecutor(
            threadPoolConfigProperties.coreSize,
            threadPoolConfigProperties.maxSize,
            threadPoolConfigProperties.keepAliveTime,
            TimeUnit.SECONDS, LinkedBlockingDeque(100000),
            Executors.defaultThreadFactory(), ThreadPoolExecutor.AbortPolicy()
        )
    }
}