package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 秒杀商品通知订阅
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_seckill_sku_notice")
data class SeckillSkuNoticeEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* member_id
	*/
		var memberId: Long? = null,
		/**
	* sku_id
	*/
		var skuId: Long? = null,
		/**
	* 活动场次id
	*/
		var sessionId: Long? = null,
		/**
	* 订阅时间
	*/
		var subcribeTime: Date? = null,
		/**
	* 发送时间
	*/
		var sendTime: Date? = null,
		/**
	* 通知方式[0-短信，1-邮件]
	*/
		var noticeType: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}