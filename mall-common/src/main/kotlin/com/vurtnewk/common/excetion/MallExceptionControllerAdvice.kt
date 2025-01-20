package com.vurtnewk.common.excetion

import com.vurtnewk.common.utils.R
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.HashMap
import java.util.function.Consumer

/**
 * 异常处理
 * @author   vurtnewk
 * @since    2025/1/13 19:13
 */
@RestControllerAdvice
class MallExceptionControllerAdvice {

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

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(e: CustomException): R {
        return R.error(BizCodeEnum.CUSTOM_EXCEPTION.code, e.message.toString())
    }

    @ExceptionHandler(value = [Throwable::class])
    fun handleException(e: Throwable): R {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.code, BizCodeEnum.UNKNOWN_EXCEPTION.msg + e.message)
    }
}