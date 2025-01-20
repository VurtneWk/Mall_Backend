package com.vurtnewk.common.utils

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import com.vurtnewk.common.excetion.BizCodeEnum
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

    companion object {
        fun error(): R {
            return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员")
        }

        fun error(msg: String): R {
            return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg)
        }

        fun error(enum: BizCodeEnum): R {
            return error(enum.code, enum.msg)
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

    }

    override fun put(key: String, value: Any): R {
        super.put(key, value)
        return this
    }

    fun putData(value: Any): R {
        this["data"] = value
        return this
    }

    /**
     * 因为泛型擦除的原因，
     * TypeReference实际就是利用匿名内部类 ，通过继承来获取运行时泛型
     */
    fun <T> getData(typeReference: TypeReference<T>): T {
        val data = this["data"]
        val str = JSON.toJSONString(data)
        return JSON.parseObject(str, typeReference)
    }

    fun <T> getData(key: String, typeReference: TypeReference<T>): T {
        val data = this[key]
        val str = JSON.toJSONString(data)
        return JSON.parseObject(str, typeReference)
    }

    fun getCode(): Int {
        return this["code"].toString().toInt()
    }


    fun isSuccess(): Boolean {
        return this.getCode() == 0
    }
}