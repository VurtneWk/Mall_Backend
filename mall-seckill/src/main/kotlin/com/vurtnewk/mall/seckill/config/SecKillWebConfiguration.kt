package com.vurtnewk.mall.seckill.config

import com.vurtnewk.mall.seckill.interceptor.LoginUserInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 配置拦截器
 * @author   vurtnewk
 * @since    2025/1/23 19:37
 */
@Configuration
class SecKillWebConfiguration(
    private val loginUserInterceptor: LoginUserInterceptor
) : WebMvcConfigurer {



    override fun addInterceptors(registry: InterceptorRegistry) {
//        super.addInterceptors(registry)
        registry.addInterceptor(loginUserInterceptor)
            .addPathPatterns("/**") //所有请求都要走这个用户
    }
}