package com.vurtnewk.common.excetion

/**
 * createTime:  2025/1/8 12:41
 *
 * author:      vurtnewk
 *
 * 错误码列表：
 * - 10 通用
 *      - 001 : 参数格式校验
 *      - 002 : 短信验证码评率太高
 * - 11 商品
 * - 12 订单
 * - 13 购物车
 * - 14 物流
 * - 15 用户
 */
enum class BizCodeEnum(
    val code: Int,
    val msg: String
) {
    UNKNOWN_EXCEPTION(10000, "系统位置异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    SMS_CODE_EXCEPTION(10002, "验证码获取频率太高，稍后再试"),

    CUSTOM_EXCEPTION(10666, "自定义异常"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架错误"),

    USER_EXIST_EXCEPTION(15001, "用户存在"),
    PHONE_EXIST_EXCEPTION(15002, "手机号存在"),
}