package com.vurtnewk.mall.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer

/**
 * spring session 配置
 * @author   vurtnewk
 * @since    2025/1/21 19:19
 */
@Configuration
class MallSessionConfig {

    @Bean
    fun cookieSerializer(): CookieSerializer {
        val defaultCookieSerializer = DefaultCookieSerializer()
        //放大作用域
        defaultCookieSerializer.setDomainName("mall.com")
//        defaultCookieSerializer.setCookieName("")
        return defaultCookieSerializer
    }

    /**
     * 自定义 session 的序列化 方法为 json
     */
    @Bean
    fun springSessionDefaultRedisSerializer(): RedisSerializer<Any> {
        return GenericJackson2JsonRedisSerializer()
    }
}