package com.vurtnewk.mall.member.config

import com.vurtnewk.mall.member.interceptor.LoginUserInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 16:55
 */
@Configuration
class MemberWebConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoginUserInterceptor()).addPathPatterns("/**")
    }

}