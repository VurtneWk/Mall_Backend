package com.vurtnewk.common.utils

import org.apache.http.HttpStatus

/**
 * createTime:  2025/1/6 01:52
 * author:      vurtnewk
 * description: 返回数据
 */
class R : HashMap<String, Any>() {

    init {
        put("code", 0)
        put("msg", "success")
    }

    fun error(): R {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员")
    }

    fun error(msg: String): R {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg)
    }

    fun error(code: Int, msg: String): R {
        val r = R()
        r["code"] = code
        r["msg"] = msg
        return r
    }

    fun ok(msg: String): R {
        val r = R()
        r["msg"] = msg
        return r
    }

    fun ok(map: Map<String, Any>): R {
        val r = R()
        r.putAll(map)
        return r
    }

    fun ok(): R {
        return R()
    }

    override fun put(key: String, value: Any): R {
        super.put(key, value)
        return this
    }

    fun getCode(): Int {
        return this["code"].toString().toInt()
    }
}