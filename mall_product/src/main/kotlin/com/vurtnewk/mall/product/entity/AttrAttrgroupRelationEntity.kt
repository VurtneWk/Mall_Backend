package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 属性&属性分组关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_attr_attrgroup_relation")
data class AttrAttrgroupRelationEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 属性id
	*/
		var attrId: Long? = null,
		/**
	* 属性分组id
	*/
		var attrGroupId: Long? = null,
		/**
	* 属性组内排序
	*/
		var attrSort: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}