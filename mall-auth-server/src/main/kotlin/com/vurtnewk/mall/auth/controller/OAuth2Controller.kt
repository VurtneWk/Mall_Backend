package com.vurtnewk.mall.auth.controller

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import com.vurtnewk.common.utils.HttpUtils
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.auth.feign.MemberFeignService
import com.vurtnewk.common.vo.MemberRespVo
import com.vurtnewk.mall.auth.vo.SocialUser
import jakarta.servlet.http.HttpSession
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * 社交登录请求
 * @author   vurtnewk
 * @since    2025/1/21 15:39
 */
@Controller
class OAuth2Controller(
    private val memberFeignService: MemberFeignService,
) {


    @GetMapping("/oauth2.0/weibo/success")
    fun webBo(@RequestParam("code") code: String, httpSession: HttpSession): String {
        // 根据 code 换取 accessToken
        val map = hashMapOf(
            "client_id" to "",
            "client_secret" to "",
            "grant_type" to "authorization_code",
            "redirect_uri" to "http://auth.mall.com/oauth2.0/weibo/success",
            "code" to code
        )
        // 换去 accessToken
        val res = HttpUtils.doPost("api.weibo.com", "/oauth2/access_token", "post", null, null, map)
        return if (res.statusLine.statusCode == 200) {
            val json = EntityUtils.toString(res.entity)
            val socialUser = JSON.parseObject(json, SocialUser::class.java)
            // 1、当前用户如果是第一次进网站，自动注册
            //登录或者注册这个社交账号
            val r = memberFeignService.oauthLogin(socialUser)
            if (r.isSuccess()) {
                val data = r.getData(object : TypeReference<MemberRespVo>() {})
                httpSession.setAttribute("loginUser", data)
                logInfo("登录成功，用户:$data")
                "redirect:http://mall.com"
            } else {
                "redirect:http://mall.com/login.html"
            }
        } else {
            "redirect:http://mall.com/login.html"
        }
    }
}