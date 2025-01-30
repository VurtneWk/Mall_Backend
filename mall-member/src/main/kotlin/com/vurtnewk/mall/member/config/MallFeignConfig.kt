package com.vurtnewk.mall.member.config

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * 网页的远程调用 需要加上 cookies 信息 , 不然会丢失登录信息
 * @author   vurtnewk
 * @since    2025/1/23 22:38
 */
@Configuration
class MallFeignConfig {

    @Bean("requestInterceptor")
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor {
            val attributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
            // 给新请求设置上老请求的Cookie
            it.header("Cookie", attributes?.request?.getHeader("Cookie"))
        }
    }
}