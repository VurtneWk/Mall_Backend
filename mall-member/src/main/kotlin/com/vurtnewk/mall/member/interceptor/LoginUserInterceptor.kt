package com.vurtnewk.mall.member.interceptor

import com.vurtnewk.common.constants.AuthServerConstants
import com.vurtnewk.common.vo.MemberRespVo
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.servlet.HandlerInterceptor

/**
 * 登录拦截器
 * @author   vurtnewk
 * @since    2025/1/23 19:36
 */
@Component
class LoginUserInterceptor : HandlerInterceptor {

    companion object {
        val loginUserThreadLocal = ThreadLocal<MemberRespVo>()
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 配置使用这个路径时 可以不登录
        val match = AntPathMatcher().match("/member/**", request.requestURI)
        if (match) return true

        val loginUser = request.session.getAttribute(AuthServerConstants.LOGIN_USER) as? MemberRespVo
        return if (loginUser != null) {
            loginUserThreadLocal.set(loginUser)
            true
        } else {
            //没登录
            request.session.setAttribute("msg", "请先进行登录")
            response.sendRedirect("http://auth.mall.com/login.html")
            false
        }

    }
}