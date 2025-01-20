package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 品牌分类关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_category_brand_relation")
data class CategoryBrandRelationEntity(
		/**
	* 
	*/
		@TableId
		var id: Long? = null,
		/**
	* 品牌id
	*/
		var brandId: Long? = null,
		/**
	* 分类id
	*/
		var catelogId: Long? = null,
		/**
	* 
	*/
		var brandName: String? = null,
		/**
	* 
	*/
		var catelogName: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}