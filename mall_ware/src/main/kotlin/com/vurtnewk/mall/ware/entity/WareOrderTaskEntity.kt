package com.vurtnewk.mall.ware.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 库存工作单
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@TableName("wms_ware_order_task")
data class WareOrderTaskEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* order_id
	*/
		var orderId: Long? = null,
		/**
	* order_sn
	*/
		var orderSn: String? = null,
		/**
	* 收货人
	*/
		var consignee: String? = null,
		/**
	* 收货人电话
	*/
		var consigneeTel: String? = null,
		/**
	* 配送地址
	*/
		var deliveryAddress: String? = null,
		/**
	* 订单备注
	*/
		var orderComment: String? = null,
		/**
	* 付款方式【 1:在线付款 2:货到付款】
	*/
		var paymentWay: Int? = null,
		/**
	* 任务状态
	*/
		var taskStatus: Int? = null,
		/**
	* 订单描述
	*/
		var orderBody: String? = null,
		/**
	* 物流单号
	*/
		var trackingNo: String? = null,
		/**
	* create_time
	*/
		var createTime: Date? = null,
		/**
	* 仓库id
	*/
		var wareId: Long? = null,
		/**
	* 工作单备注
	*/
		var taskComment: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}