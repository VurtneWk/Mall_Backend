package com.vurtnewk.mall.cart.config

import com.vurtnewk.mall.cart.interceptor.CartInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 13:01
 */
@Configuration
class MallWebConfig : WebMvcConfigurer {

    /**
     * 添加拦截器
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(CartInterceptor())
            .addPathPatterns("/**")
    }
}