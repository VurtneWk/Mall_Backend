package com.vurtnewk.mall.member.vo

/**
 * 微博社交登录返回的数据
 * @author   vurtnewk
 * @since    2025/1/21 16:04
 */
data class SocialUser(
    val access_token: String,
    val remind_in: String,
    val expires_in: String,
    val uid: String,
    val isRealName: String,
)
