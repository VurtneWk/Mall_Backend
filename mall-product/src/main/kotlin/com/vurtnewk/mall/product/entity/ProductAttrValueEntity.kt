package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * spu属性值
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_product_attr_value")
data class ProductAttrValueEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 商品id
	*/
		var spuId: Long? = null,
		/**
	* 属性id
	*/
		var attrId: Long? = null,
		/**
	* 属性名
	*/
		var attrName: String? = null,
		/**
	* 属性值
	*/
		var attrValue: String? = null,
		/**
	* 顺序
	*/
		var attrSort: Int? = null,
		/**
	* 快速展示【是否展示在介绍上；0-否 1-是】
	*/
		var quickShow: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}