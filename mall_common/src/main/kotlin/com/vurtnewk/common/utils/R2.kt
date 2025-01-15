package com.vurtnewk.common.utils

import org.apache.http.HttpStatus

/**
 * createTime:  2025/1/6 01:52
 * author:      vurtnewk
 * description: 返回数据
 */
class R2<T>(var data: T? = null) : HashMap<String, Any>() {

    init {
        put("code", 0)
        put("msg", "success")
    }


    companion object {
        fun <T> error(): R2<T> {
            return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员")
        }

        fun <T> error(msg: String): R2<T> {
            return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg)
        }

        fun <T> error(code: Int, msg: String): R2<T> {
            val r = R2<T>()
            r["code"] = code
            r["msg"] = msg
            return r
        }

        fun <T> ok(msg: String): R2<T> {
            val r = R2<T>()
            r["msg"] = msg
            return r
        }

        fun <T> ok(map: Map<String, Any>): R2<T> {
            val r = R2<T>()
            r.putAll(map)
            return r
        }

         fun  <T> ok(): R2<T> {
            return R2()
        }

    }


    override fun put(key: String, value: Any): R2<T> {
        super.put(key, value)
        return this
    }

    fun putData(value: Any): R2<T> {
        this["data"] = value
        return this
    }

    fun getCode(): Int {
        return this["code"].toString().toInt()
    }


    fun isSuccess(): Boolean {
        return this.getCode() == 0
    }
}