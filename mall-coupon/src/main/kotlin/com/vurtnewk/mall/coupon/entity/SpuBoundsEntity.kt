package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 商品spu积分设置
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_spu_bounds")
data class SpuBoundsEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 
	*/
		var spuId: Long? = null,
		/**
	* 成长积分
	*/
		var growBounds: BigDecimal? = null,
		/**
	* 购物积分
	*/
		var buyBounds: BigDecimal? = null,
		/**
	* 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]
	*/
		var work: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}