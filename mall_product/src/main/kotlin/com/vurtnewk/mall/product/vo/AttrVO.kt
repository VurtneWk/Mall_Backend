package com.vurtnewk.mall.product.vo

import com.baomidou.mybatisplus.annotation.TableId
import java.io.Serializable

/**
 * 商品属性 Vo
 * @author   vurtnewk
 * @since    2025/1/10 17:17
 */

data class AttrVO(
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
     * 可选值列表(用逗号分隔)
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
     * 属性所属的分组
     */
    var attrGroupId: Long? = null
) : Serializable