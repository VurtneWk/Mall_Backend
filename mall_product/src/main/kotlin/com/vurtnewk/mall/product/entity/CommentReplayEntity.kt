package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 商品评价回复关系
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_comment_replay")
data class CommentReplayEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 评论id
	*/
		var commentId: Long? = null,
		/**
	* 回复id
	*/
		var replyId: Long? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}