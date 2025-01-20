package com.vurtnewk.mall.auth.controller

import com.vurtnewk.common.constants.AuthServerConstants
import com.vurtnewk.common.constants.ThirdPartyConstants
import com.vurtnewk.common.excetion.BizCodeEnum
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.auth.feign.ThirdPartFeignService
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * 登录
 * @author   vurtnewk
 * @since    2025/1/20 12:29
 */
@Controller
class LoginController(
    private val mThreadPartFeignService: ThirdPartFeignService,
    private val mStringRedisTemplate: StringRedisTemplate,
) {

//    @GetMapping("/login.html")
//    fun loginPage() = "login"
//
//    @GetMapping("/reg.html")
//    fun regPage() = "reg"


    @GetMapping("/sms/sendCode")
    @ResponseBody
    fun sendCode(@RequestParam("phone") phone: String): R {
        //todo 接口防刷

        // 防止1分钟内再次请求发送验证码
        val redisCode = mStringRedisTemplate.opsForValue().get(AuthServerConstants.SMS_CODE_CACHE_PREFIX + phone)
        if (redisCode != null) {
            // 上一次发送验证码的时间
            val lastSendTime = redisCode.split("_")[1]
            if (System.currentTimeMillis() - lastSendTime.toLong() < 60000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.code, BizCodeEnum.SMS_CODE_EXCEPTION.msg)
            }
        }
        val code = "${UUID.randomUUID().toString().subSequence(0, 5)}_${System.currentTimeMillis()}"
        mStringRedisTemplate.opsForValue()
            .set(
                AuthServerConstants.SMS_CODE_CACHE_PREFIX + phone,
                code,
                ThirdPartyConstants.SMS_CODE_VALID_TIME,
                TimeUnit.MINUTES
            )


        mThreadPartFeignService.sendCode(phone, code)
        return R.ok()
    }
}