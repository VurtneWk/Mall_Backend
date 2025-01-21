package com.vurtnewk.mall.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Spring MVC 配置
 * @author   vurtnewk
 * @since    2025/1/20 15:43
 */
@Configuration
class MallWebConfig : WebMvcConfigurer {

    /**
     * 视图映射
     * 代替下面这种方法
     * ```kotlin
     *     @GetMapping("/login.html")
     *     fun loginPage() = "login"
     * ```
     */
    override fun addViewControllers(registry: ViewControllerRegistry) {
//        super.addViewControllers(registry)
//        registry.addViewController("/login.html").setViewName("login")
        registry.addViewController("/reg.html").setViewName("reg")
    }

}