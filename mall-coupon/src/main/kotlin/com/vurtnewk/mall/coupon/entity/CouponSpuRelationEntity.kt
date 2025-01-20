package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 优惠券与产品关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_coupon_spu_relation")
data class CouponSpuRelationEntity(
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
	* spu_id
	*/
		var spuId: Long? = null,
		/**
	* spu_name
	*/
		var spuName: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}