package com.vurtnewk.mall.order.config

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 *
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