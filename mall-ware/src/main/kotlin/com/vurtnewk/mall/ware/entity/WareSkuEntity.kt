package com.vurtnewk.mall.ware.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 商品库存
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@TableName("wms_ware_sku")
data class WareSkuEntity(
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
	* 仓库id
	*/
		var wareId: Long? = null,
		/**
	* 库存数
	*/
		var stock: Int? = null,
		/**
	* sku_name
	*/
		var skuName: String? = null,
		/**
	* 锁定库存
	*/
		var stockLocked: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}