package com.vurtnewk.mall.member.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 会员登录记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@TableName("ums_member_login_log")
data class MemberLoginLogEntity(
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
	* 创建时间
	*/
		var createTime: Date? = null,
		/**
	* ip
	*/
		var ip: String? = null,
		/**
	* city
	*/
		var city: String? = null,
		/**
	* 登录类型[1-web，2-app]
	*/
		var loginType: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}