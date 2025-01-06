package com.vurtnewk.mall.order.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 支付信息表
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@TableName("oms_payment_info")
data class PaymentInfoEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 订单号（对外业务号）
	*/
		var orderSn: String? = null,
		/**
	* 订单id
	*/
		var orderId: Long? = null,
		/**
	* 支付宝交易流水号
	*/
		var alipayTradeNo: String? = null,
		/**
	* 支付总金额
	*/
		var totalAmount: BigDecimal? = null,
		/**
	* 交易内容
	*/
		var subject: String? = null,
		/**
	* 支付状态
	*/
		var paymentStatus: String? = null,
		/**
	* 创建时间
	*/
		var createTime: Date? = null,
		/**
	* 确认时间
	*/
		var confirmTime: Date? = null,
		/**
	* 回调内容
	*/
		var callbackContent: String? = null,
		/**
	* 回调时间
	*/
		var callbackTime: Date? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}