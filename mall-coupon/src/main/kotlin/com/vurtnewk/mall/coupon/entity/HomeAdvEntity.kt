package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 首页轮播广告
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_home_adv")
data class HomeAdvEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 名字
	*/
		var name: String? = null,
		/**
	* 图片地址
	*/
		var pic: String? = null,
		/**
	* 开始时间
	*/
		var startTime: Date? = null,
		/**
	* 结束时间
	*/
		var endTime: Date? = null,
		/**
	* 状态
	*/
		var status: Int? = null,
		/**
	* 点击数
	*/
		var clickCount: Int? = null,
		/**
	* 广告详情连接地址
	*/
		var url: String? = null,
		/**
	* 备注
	*/
		var note: String? = null,
		/**
	* 排序
	*/
		var sort: Int? = null,
		/**
	* 发布者
	*/
		var publisherId: Long? = null,
		/**
	* 审核者
	*/
		var authId: Long? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}