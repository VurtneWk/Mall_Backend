package com.vurtnewk.common.utils

import com.baomidou.mybatisplus.core.metadata.IPage
import com.fasterxml.jackson.annotation.JsonInclude
import org.apache.http.HttpStatus

/**
 * createTime:  2025/1/6 01:52
 * author:      vurtnewk
 * description: 返回数据
 */
data class R2<T>(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var data: T? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var page: IPage<T>? = null,
    var code: Int = 0,
    var msg: String = "success",
) {

    companion object {
        fun <T> error(): R2<T> {
            return R2(null, null, HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员")
        }

        fun <T> error(code: Int, msg: String): R2<T> {
            return R2(null, null, code, msg)
        }

        fun <T> ok(data: T? = null, page: IPage<T>? = null): R2<T> {
            return R2(data, page, 0, "success")
        }

        fun <T> ok(): R2<T> {
            return R2(null, null, 0, "success")
        }
    }

    fun isSuccess(): Boolean = code == 0
}