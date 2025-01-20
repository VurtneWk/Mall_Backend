package com.vurtnewk.common.valid

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * 配合[ListIntValue]使用
 * @author   vurtnewk
 * @since    2025/1/8 14:17
 */
class ListIntValueConstraintValidator : ConstraintValidator<ListIntValue, Int> {

    private val set = mutableSetOf<Int>()

    /**
     * 初始化的方法
     */
    override fun initialize(constraintAnnotation: ListIntValue?) {
        super.initialize(constraintAnnotation)
        println(constraintAnnotation?.message)
        constraintAnnotation?.values?.forEach {
            set.add(it)
        }
    }
    /**
     * 校验数据
     * @param value 需要校验的值
     */
    override fun isValid(value: Int?, context: ConstraintValidatorContext?): Boolean {
        return set.contains(value)
    }

}