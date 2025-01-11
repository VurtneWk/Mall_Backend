package com.vurtnewk.mall.product.vo

import com.vurtnewk.mall.product.entity.AttrEntity

/**
 * 属性分组以及每个分组下的所有属性
 * @author   vurtnewk
 * @since    2025/1/12 00:08
 */
data class AttrGroupWithAttrsVO(
    /**
     * 分组id
     */
    var attrGroupId: Long? = null,
    /**
     * 组名
     */
    var attrGroupName: String? = null,
    /**
     * 排序
     */
    var sort: Int? = null,
    /**
     * 描述
     */
    var descript: String? = null,
    /**
     * 组图标
     */
    var icon: String? = null,
    /**
     * 所属分类id
     */
    var catelogId: Long? = null,
    /**
     * 分组下的所有属性
     */
    var attrs: List<AttrEntity>? = null
)
