package com.vurtnewk.mall.auth.controller

import com.vurtnewk.common.constants.AuthServerConstants
import com.vurtnewk.common.constants.ThirdPartyConstants
import com.vurtnewk.common.excetion.BizCodeEnum
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.auth.feign.ThirdPartFeignService
import com.vurtnewk.mall.auth.vo.UserRegisterVo
import jakarta.validation.Valid
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes
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

    /**
     * 获取短信验证码
     */
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

    /**
     * 注册
     * @param redirectAttributes 重定向携带数据，利用session原理。将数据放到session中，
     * 只要跳到下一个页面取出数据以后，session里面的数据就会被删掉 //TODO 分布式下的session问题
     * @param userRegisterVo 这里参数是form提交的，不能加 @RequestBody ， @RequestBody 用户json
     */
    @PostMapping("/register")
    fun register(
        @Valid userRegisterVo: UserRegisterVo,
        result: BindingResult,
//        model: Model,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (result.hasErrors()) {
            val errors = result.fieldErrors.associate { it.field to it.defaultMessage.toString() }
//            model.addAttribute("errors", errors)
            /**
             * 1. 校验出错 如果使用转发到注册页
             *
             * 'POST' Not supported 原因: 用户 -> /register(post) -> 转发 /reg.html ，
             * 路径映射默认都是get方式访问！
             *
             * 2. 转发时: redirect:/reg.html
             *
             * 默认跳转地址会是  http://192.168.3.26:5800/reg.html，需要自己改"redirect:http://auth.mall.com/reg.html"
             *
             * 3. 转发时要携带数据 需要使用 redirectAttributes，重定向时，Model 不起作用，因为它的数据仅适用于当前请求生命周期。
             *
             * - 方法	            数据作用范围	  是否显示在地址栏	   适用场景
             * - addFlashAttribute	下一次请求有效（临时）	否	临时提示信息（如错误信息）
             * - addAttribute	始终有效（作为查询参数）	是	显示需要的数据（如搜索条件）
             *
             */
            redirectAttributes.addFlashAttribute("errors", errors)
            return "redirect:http://auth.mall.com/reg.html"
        }
        return "redirect:/login.html"
    }
}