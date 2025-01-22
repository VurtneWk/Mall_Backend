package com.vurtnewk.mall.cart.interceptor

import com.vurtnewk.common.constants.AuthServerConstants
import com.vurtnewk.common.constants.CartConstants
import com.vurtnewk.common.vo.MemberRespVo
import com.vurtnewk.mall.cart.vo.UserInfoDto
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.UUID

/**
 * 在执行目标方法之前，判断用户的登录状态，并封装传递给controller目标请求
 * @author   vurtnewk
 * @since    2025/1/22 12:16
 */
@Component
class CartInterceptor : HandlerInterceptor {

    companion object {
        // 线程隔离的数据存储
        val threadLocal = ThreadLocal<UserInfoDto>()
    }


    /**
     * 目标方法执行之前
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val memberRespVo = request.session.getAttribute(AuthServerConstants.LOGIN_USER) as? MemberRespVo
        val userInfoDto = UserInfoDto()
        if (memberRespVo != null) {
            userInfoDto.userId = memberRespVo.id
        }
        request.cookies?.forEach { cookie ->
            if (CartConstants.TEMP_USER_COOKIE_NAME.equals(cookie.name, ignoreCase = true)) {
                userInfoDto.userKey = cookie.value
                userInfoDto.tempUser = true
            }
        }
        // 如果没有临时用户，创建一个临时用户
        if (userInfoDto.userKey.isNullOrEmpty()) {
            userInfoDto.userKey = UUID.randomUUID().toString()
        }
        threadLocal.set(userInfoDto)
        return true
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(request, response, handler, modelAndView)
        // tempUser
        if (!threadLocal.get().tempUser) {
            val cookie = Cookie(CartConstants.TEMP_USER_COOKIE_NAME, threadLocal.get().userKey)
            cookie.domain = "mall.com"
            cookie.maxAge = CartConstants.TEMP_USER_COOKIE_TIMEOUT
            response.addCookie(cookie)
        }
    }
}