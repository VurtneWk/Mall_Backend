package com.vurtnewk.mall.order.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 退货原因
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@TableName("oms_order_return_reason")
data class OrderReturnReasonEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 退货原因名
	*/
		var name: String? = null,
		/**
	* 排序
	*/
		var sort: Int? = null,
		/**
	* 启用状态
	*/
		var status: Int? = null,
		/**
	* create_time
	*/
		var createTime: Date? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}