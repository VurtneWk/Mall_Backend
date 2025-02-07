package com.vurtnewk.mall.seckill.config

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler
import com.alibaba.fastjson2.JSON
import com.vurtnewk.common.excetion.BizCodeEnum
import com.vurtnewk.common.utils.R
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 配置限流时 返回的数据
 * @author   vurtnewk
 * @since    2025/2/7 16:04
 */
@Configuration
class SecKillSentinelConfig : WebMvcConfigurer {

    @Bean
    fun sentinelBlockExceptionHandler(): BlockExceptionHandler {
        return BlockExceptionHandler { _, response, _ ->
            response.characterEncoding = "UTF-8"
            response.contentType = "application/json"
            response.writer.write(JSON.toJSONString(R.error(BizCodeEnum.TOO_MANY_REQUEST_EXCEPTION)))
        }
    }
}