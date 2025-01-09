package com.vurtnewk.mall.product.excetion

import com.vurtnewk.common.excetion.BizCodeEnum
import com.vurtnewk.common.utils.R
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.HashMap
import java.util.function.Consumer

/**
 * createTime:  2025/1/8 12:37
 * author:      vurtnewk
 * description: 统一异常处理
 */
@RestControllerAdvice(basePackages = ["com.vurtnewk.mall.product.controller"])
class MallProductExceptionControllerAdvice {
    /**
     * 用于JSR303校验异常时的数据返回
     */
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleValidException(e: MethodArgumentNotValidException): R {
        val map: MutableMap<String, String?> = HashMap()
        e.bindingResult.fieldErrors.forEach(Consumer { item: FieldError ->
            map[item.field] = item.defaultMessage
        })
        return R.error(BizCodeEnum.VALID_EXCEPTION.code, BizCodeEnum.VALID_EXCEPTION.msg).put("data", map)
    }


//    @ExceptionHandler(value = [Throwable::class])
//    fun handleException(e:Throwable): R {
//        println(e.message)
//        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.code, BizCodeEnum.UNKNOWN_EXCEPTION.msg)
//    }
}