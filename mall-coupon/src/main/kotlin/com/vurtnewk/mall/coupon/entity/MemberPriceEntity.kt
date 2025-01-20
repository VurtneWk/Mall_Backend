package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 商品会员价格
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_member_price")
data class MemberPriceEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* sku_id
	*/
		var skuId: Long? = null,
		/**
	* 会员等级id
	*/
		var memberLevelId: Long? = null,
		/**
	* 会员等级名
	*/
		var memberLevelName: String? = null,
		/**
	* 会员对应价格
	*/
		var memberPrice: BigDecimal? = null,
		/**
	* 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
	*/
		var addOther: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}