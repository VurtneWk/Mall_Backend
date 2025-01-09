package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.vurtnewk.common.valid.AddGroup
import com.vurtnewk.common.valid.ListIntValue
import com.vurtnewk.common.valid.UpdateGroup
import com.vurtnewk.common.valid.UpdateStatusGroup
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
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
    @field:Null(message = "新增不能指定ID", groups = [AddGroup::class])
    @field:NotNull(message = "修改必须指定品牌ID", groups = [UpdateGroup::class])
    var brandId: Long? = null,
    /**
     * 品牌名
     */
    @field:NotBlank(message = "品牌名不能为空", groups = [AddGroup::class, UpdateGroup::class])
    var name: String? = null,
    /**
     * 品牌logo地址
     */
    @field:URL(message = "logo必须是一个合法的url地址", groups = [AddGroup::class, UpdateGroup::class])
    @field:NotBlank(groups = [AddGroup::class])
    var logo: String = "",
    /**
     * 介绍
     */
    var descript: String = "",
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @field:ListIntValue(values = [1, 0], groups = [AddGroup::class, UpdateStatusGroup::class])
    @field:NotNull(groups = [AddGroup::class, UpdateStatusGroup::class])
    var showStatus: Int = 1,
    /**
     * 检索首字母
     */
    @field:NotEmpty(groups = [AddGroup::class])
    @field:Pattern(
        regexp = "^[a-zA-Z]$",
        message = "检索首字母必须是一个字母",
        groups = [AddGroup::class, UpdateGroup::class]
    )
    var firstLetter: String = "",
    /**
     * 排序
     */
    @field:NotNull(groups = [AddGroup::class])
    @field:Min(value = 0, message = "排序必须大于等于0", groups = [AddGroup::class, UpdateGroup::class])
    var sort: Int = 0,
) : Serializable {
    companion object {
        @JvmStatic
        val serialVersionUID: Long = 1L
    }
}