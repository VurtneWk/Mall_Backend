package com.vurtnewk.mall.member.excetion

/**
 * 会员的异常类
 * @author   vurtnewk
 * @since    2025/1/21 01:28
 */

class UsernameExistException(message: String = "用户名已存在") : RuntimeException(message)

class PhoneExistException(message: String = "手机号已存在") : RuntimeException(message)