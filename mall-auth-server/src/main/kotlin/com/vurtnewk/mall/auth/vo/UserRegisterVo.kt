package com.vurtnewk.mall.auth.vo

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

/**
 * 用户注册
 * @author   vurtnewk
 * @since    2025/1/20 19:18
 */
data class UserRegisterVo(
    @field:NotEmpty(message = "用户名必须提交")
    @field:Length(min = 6, max = 18, message = "用户名必须是6-18位字符")
    var userName: String = "",

    @field:NotEmpty(message = "密码必须填写")
    @field:Length(min = 6, max = 18, message = "密码必须是6-18位字符")
    var password: String = "",

    @field:NotEmpty(message = "手机号必须添加")
    @field:Pattern(regexp = "^1[3-9][0-9]{9}$" , message = "手机号格式不正确")
    var phone: String = "",

    @field:NotEmpty(message = "验证码必须添加")
    var code: String = "",
)
