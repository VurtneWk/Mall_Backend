package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * sku销售属性&值
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@TableName("pms_sku_sale_attr_value")
data class SkuSaleAttrValueEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* sku_id
	*/
		var skuId: Long? = null,
		/**
	* attr_id
	*/
		var attrId: Long? = null,
		/**
	* 销售属性名
	*/
		var attrName: String? = null,
		/**
	* 销售属性值
	*/
		var attrValue: String? = null,
		/**
	* 顺序
	*/
		var attrSort: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}