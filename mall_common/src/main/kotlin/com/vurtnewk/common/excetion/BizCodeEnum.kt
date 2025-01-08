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
    VALID_EXCEPTION(10001, "参数格式校验失败");

}