package com.vurtnewk.mall.member.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 会员收藏的商品
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@TableName("ums_member_collect_spu")
data class MemberCollectSpuEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 会员id
	*/
		var memberId: Long? = null,
		/**
	* spu_id
	*/
		var spuId: Long? = null,
		/**
	* spu_name
	*/
		var spuName: String? = null,
		/**
	* spu_img
	*/
		var spuImg: String? = null,
		/**
	* create_time
	*/
		var createTime: Date? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}