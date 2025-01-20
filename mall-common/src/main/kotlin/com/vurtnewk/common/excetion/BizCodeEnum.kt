package com.vurtnewk.common.excetion

/**
 * createTime:  2025/1/8 12:41
 * author:      vurtnewk
 * description:
 */
enum class BizCodeEnum(
    val code: Int,
    val msg: String
) {
    UNKNOWN_EXCEPTION(10000, "系统位置异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    CUSTOM_EXCEPTION(10002, "自定义异常"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架错误"),
    SMS_CODE_EXCEPTION(10002, "验证码获取频率太高，稍后再试"),
}