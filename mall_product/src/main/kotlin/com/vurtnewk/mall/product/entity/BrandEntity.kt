package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.URL
import java.io.Serializable

/**
 * 品牌
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_brand")
data class BrandEntity(
    /**
     * 品牌id
     */
    @TableId
    var brandId: Long? = null,
    /**
     * 品牌名
     */
    @field:NotBlank(message = "品牌名不能为空")
    var name: String = "",
    /**
     * 品牌logo地址
     */
    @field:URL(message = "logo必须是一个合法的url地址")
    @field:NotEmpty
    var logo: String = "",
    /**
     * 介绍
     */
    var descript: String = "",
    /**
     * 显示状态[0-不显示；1-显示]
     */
    var showStatus: Int = 1,
    /**
     * 检索首字母
     */
    @field:NotEmpty
    @field:Pattern(regexp = "/^[a-zA-Z]$/", message = "检索首字母必须是一个字母")
    var firstLetter: String = "",
    /**
     * 排序
     */
    @field:NotNull
    @field:Min(value = 0, message = "排序必须大于等于0")
    var sort: Int = 0,
) : Serializable {
    companion object {
        @JvmStatic
        val serialVersionUID: Long = 1L
    }
}