package com.vurtnewk.mall.member.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 成长值变化历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@TableName("ums_growth_change_history")
data class GrowthChangeHistoryEntity(
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
	* create_time
	*/
		var createTime: Date? = null,
		/**
	* 改变的值（正负计数）
	*/
		var changeCount: Int? = null,
		/**
	* 备注
	*/
		var note: String? = null,
		/**
	* 积分来源[0-购物，1-管理员修改]
	*/
		var sourceType: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}