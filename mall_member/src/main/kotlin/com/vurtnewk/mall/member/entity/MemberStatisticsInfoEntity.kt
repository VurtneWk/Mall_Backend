package com.vurtnewk.mall.member.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 会员统计信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@TableName("ums_member_statistics_info")
data class MemberStatisticsInfoEntity(
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
	* 累计消费金额
	*/
		var consumeAmount: BigDecimal? = null,
		/**
	* 累计优惠金额
	*/
		var couponAmount: BigDecimal? = null,
		/**
	* 订单数量
	*/
		var orderCount: Int? = null,
		/**
	* 优惠券数量
	*/
		var couponCount: Int? = null,
		/**
	* 评价数
	*/
		var commentCount: Int? = null,
		/**
	* 退货数量
	*/
		var returnOrderCount: Int? = null,
		/**
	* 登录次数
	*/
		var loginCount: Int? = null,
		/**
	* 关注数量
	*/
		var attendCount: Int? = null,
		/**
	* 粉丝数量
	*/
		var fansCount: Int? = null,
		/**
	* 收藏的商品数量
	*/
		var collectProductCount: Int? = null,
		/**
	* 收藏的专题活动数量
	*/
		var collectSubjectCount: Int? = null,
		/**
	* 收藏的评论数量
	*/
		var collectCommentCount: Int? = null,
		/**
	* 邀请的朋友数量
	*/
		var inviteFriendCount: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}