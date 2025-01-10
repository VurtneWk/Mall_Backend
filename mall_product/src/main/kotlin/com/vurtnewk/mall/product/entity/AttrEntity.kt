package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.util.Date

/**
 * 商品属性
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_attr")
data class AttrEntity(
    /**
     * 属性id
     */
    @TableId
    var attrId: Long? = null,
    /**
     * 属性名
     */
    var attrName: String? = null,
    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    var searchType: Int? = null,
    /**
     * 属性图标
     */
    var icon: String? = null,
    /**
     * 可选值列表[用逗号分隔]
     */
    var valueSelect: String? = null,
    /**
     * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
     */
    var attrType: Int? = null,
    /**
     * 启用状态[0 - 禁用，1 - 启用]
     */
    var enable: Long? = null,
    /**
     * 所属分类
     */
    var catelogId: Long? = null,
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    var showDesc: Int? = null,
    /**
     * 值类型 （0-单个值，1可以选择多个值）
     */
    var valueType: Int? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}