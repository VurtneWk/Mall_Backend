package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 秒杀活动
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_seckill_promotion")
data class SeckillPromotionEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 活动标题
	*/
		var title: String? = null,
		/**
	* 开始日期
	*/
		var startTime: Date? = null,
		/**
	* 结束日期
	*/
		var endTime: Date? = null,
		/**
	* 上下线状态
	*/
		var status: Int? = null,
		/**
	* 创建时间
	*/
		var createTime: Date? = null,
		/**
	* 创建人
	*/
		var userId: Long? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}