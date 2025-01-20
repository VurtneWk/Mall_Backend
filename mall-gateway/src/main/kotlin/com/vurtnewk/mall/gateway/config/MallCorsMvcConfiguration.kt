//package com.vurtnewk.mall.gateway.config
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.servlet.config.annotation.CorsRegistry
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
//
///**
// * createTime:  2025/1/7 07:44
// * author:      vurtnewk
// * description:
// * 如果依赖使用的spring-cloud-starter-gateway-mvc
// * 而不是spring-cloud-starter-gateway，则需要使用这个配置
// */
//@Configuration
//class MallCorsMvcConfiguration : WebMvcConfigurer {
//
//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/**") // 匹配所有路径
//            .allowedOriginPatterns("*")
////            .allowedOrigins("*") // 允许的来源
//            .allowedMethods("*") // 允许的 HTTP 方法
//            .allowedHeaders("*") // 允许的头
//            .allowCredentials(true) // 允许携带 Cookie
//    }
//}