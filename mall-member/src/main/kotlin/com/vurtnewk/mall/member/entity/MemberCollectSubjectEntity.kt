package com.vurtnewk.mall.member.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 会员收藏的专题活动
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@TableName("ums_member_collect_subject")
data class MemberCollectSubjectEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* subject_id
	*/
		var subjectId: Long? = null,
		/**
	* subject_name
	*/
		var subjectName: String? = null,
		/**
	* subject_img
	*/
		var subjectImg: String? = null,
		/**
	* 活动url
	*/
		var subjectUrll: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}