package com.vurtnewk.mall.cart.vo

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 12:19
 */
data class UserInfoDto(
    var userId: Long? = null,
    var userKey: String? = null,
    /**
     * 用于判断 浏览器 中 是否已经有临时用户
     *
     * false 没有， true 有
     *
     * 在没有临时用户的情况下，会给浏览器设置cookie 创建临时用户
     */
    var tempUser: Boolean = false,
)