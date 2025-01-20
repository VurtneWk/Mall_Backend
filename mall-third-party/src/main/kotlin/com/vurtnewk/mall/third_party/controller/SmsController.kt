package com.vurtnewk.mall.third_party.controller

import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.third_party.component.SmsComponent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * 短信相关
 * @author   vurtnewk
 * @since    2025/1/20 17:36
 */
@RestController
@RequestMapping("/sms")
class SmsController(
    private val mSmsComponent: SmsComponent,
) {

    /**
     * 提供给别的服务进行调用
     */
    @GetMapping("/sendcode")
    @ResponseBody
    fun sendCode(@RequestParam("phone") phone: String, @RequestParam("code") code: String): R {
        mSmsComponent.sendSmsCode(phone, code)
        return R.ok()
    }
}