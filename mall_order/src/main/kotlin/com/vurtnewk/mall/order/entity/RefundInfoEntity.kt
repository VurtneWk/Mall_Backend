package com.vurtnewk.mall.order.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 退款信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@TableName("oms_refund_info")
data class RefundInfoEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 退款的订单
	*/
		var orderReturnId: Long? = null,
		/**
	* 退款金额
	*/
		var refund: BigDecimal? = null,
		/**
	* 退款交易流水号
	*/
		var refundSn: String? = null,
		/**
	* 退款状态
	*/
		var refundStatus: Int? = null,
		/**
	* 退款渠道[1-支付宝，2-微信，3-银联，4-汇款]
	*/
		var refundChannel: Int? = null,
		/**
	* 
	*/
		var refundContent: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}