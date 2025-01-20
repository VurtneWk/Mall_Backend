package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

/**
 * 商品三级分类
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_category")
data class CategoryEntity(
    /**
     * 分类id
     */
    @TableId
    var catId: Long? = null,
    /**
     * 分类名称
     */
    var name: String? = null,
    /**
     * 父分类id
     */
    var parentCid: Long? = null,
    /**
     * 层级
     */
    var catLevel: Int? = null,
    /**
     * 是否显示[0-不显示，1显示]
     */
    @TableLogic(value = "1", delval = "0")
    var showStatus: Int? = null,
    /**
     * 排序
     */
    var sort: Int? = null,
    /**
     * 图标地址
     */
    var icon: String? = null,
    /**
     * 计量单位
     */
    var productUnit: String? = null,
    /**
     * 商品数量
     */
    var productCount: Int? = null,
    /**
     * 当前分类的子分类
     * 因为不在数据库中存在的字段，所以需要添加@TableField(exist = false)
     * @JsonInclude(JsonInclude.Include.NON_EMPTY) 当数据时空的时候，不传给前端
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    var children: MutableList<CategoryEntity> = mutableListOf()
) : Serializable {
    companion object {
        @JvmStatic
        val serialVersionUID: Long = 1L
    }
}