package com.vurtnewk.mall.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

/**
 * createTime:  2025/1/7 06:58
 * author:      vurtnewk
 * description: 解决跨域问题
 */
@Configuration
class MallCorsConfiguration {

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()

        val corsConfiguration = CorsConfiguration().apply {
            addAllowedHeader("*")
            addAllowedMethod("*")
//            addAllowedOrigin("*")
            addAllowedOriginPattern("*")
            addExposedHeader("*")
            allowCredentials = true
        }
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)
        println("=======  corsWebFilter ========")
        return CorsWebFilter(urlBasedCorsConfigurationSource)
    }
}