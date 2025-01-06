package com.vurtnewk.mall.ware.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@TableName("wms_purchase_detail")
data class PurchaseDetailEntity(
		/**
	* 
	*/
		@TableId
		var id: Long? = null,
		/**
	* 采购单id
	*/
		var purchaseId: Long? = null,
		/**
	* 采购商品id
	*/
		var skuId: Long? = null,
		/**
	* 采购数量
	*/
		var skuNum: Int? = null,
		/**
	* 采购金额
	*/
		var skuPrice: BigDecimal? = null,
		/**
	* 仓库id
	*/
		var wareId: Long? = null,
		/**
	* 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
	*/
		var status: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}