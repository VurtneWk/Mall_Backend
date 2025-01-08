package com.vurtnewk.common.valid

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.annotation.AnnotationTarget.*

/**
 * createTime:  2025/1/8 13:57
 * author:      vurtnewk
 * description: 整数校验注解
 */
@MustBeDocumented
@Constraint(validatedBy = [ListIntValueConstraintValidator::class])
@Target(FIELD, FUNCTION, ANNOTATION_CLASS, CONSTRUCTOR, VALUE_PARAMETER, TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ListIntValue(
    //这里是错误时的提示信息 Java里能识别进行跳转，Kotlin跳转不了
    val message: String = "{com.vurtnewk.common.valid.ListIntValue.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val values: IntArray = []
)