package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 优惠券分类关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_coupon_spu_category_relation")
data class CouponSpuCategoryRelationEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 优惠券id
	*/
		var couponId: Long? = null,
		/**
	* 产品分类id
	*/
		var categoryId: Long? = null,
		/**
	* 产品分类名称
	*/
		var categoryName: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}