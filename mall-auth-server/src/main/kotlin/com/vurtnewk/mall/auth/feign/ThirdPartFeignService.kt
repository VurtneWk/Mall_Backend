package com.vurtnewk.mall.auth.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * 第三方服务
 * @author   vurtnewk
 * @since    2025/1/20 17:49
 */
@FeignClient("mall-third-party")
interface ThirdPartFeignService {

    @GetMapping("/sms/sendcode")
    fun sendCode(@RequestParam("phone") phone: String, @RequestParam("code") code: String): R
}