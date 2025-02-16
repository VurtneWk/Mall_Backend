package com.vurtnewk.mall.order.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 订单配置信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@TableName("oms_order_setting")
data class OrderSettingEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 秒杀订单超时关闭时间(分)
	*/
		var flashOrderOvertime: Int? = null,
		/**
	* 正常订单超时时间(分)
	*/
		var normalOrderOvertime: Int? = null,
		/**
	* 发货后自动确认收货时间（天）
	*/
		var confirmOvertime: Int? = null,
		/**
	* 自动完成交易时间，不能申请退货（天）
	*/
		var finishOvertime: Int? = null,
		/**
	* 订单完成后自动好评时间（天）
	*/
		var commentOvertime: Int? = null,
		/**
	* 会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】
	*/
		var memberLevel: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}