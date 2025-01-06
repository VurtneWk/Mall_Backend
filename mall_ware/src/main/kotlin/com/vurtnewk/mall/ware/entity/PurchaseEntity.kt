package com.vurtnewk.mall.ware.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 采购信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@TableName("wms_purchase")
data class PurchaseEntity(
		/**
	* 采购单id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 采购人id
	*/
		var assigneeId: Long? = null,
		/**
	* 采购人名
	*/
		var assigneeName: String? = null,
		/**
	* 联系方式
	*/
		var phone: String? = null,
		/**
	* 优先级
	*/
		var priority: Int? = null,
		/**
	* 状态
	*/
		var status: Int? = null,
		/**
	* 仓库id
	*/
		var wareId: Long? = null,
		/**
	* 总金额
	*/
		var amount: BigDecimal? = null,
		/**
	* 创建日期
	*/
		var createTime: Date? = null,
		/**
	* 更新日期
	*/
		var updateTime: Date? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}