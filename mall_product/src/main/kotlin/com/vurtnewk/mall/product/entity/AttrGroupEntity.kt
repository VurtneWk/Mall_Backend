package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.util.Date

/**
 * 属性分组
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_attr_group")
data class AttrGroupEntity(
    /**
     * 分组id
     */
    @TableId var attrGroupId: Long? = null,
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
     *  @TableField(exist = false) 数据库中不存在
     */
    @TableField(exist = false)
    var catelogPath: List<Long>? = emptyList()
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}