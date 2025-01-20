package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 商品满减信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_sku_full_reduction")
data class SkuFullReductionEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* spu_id
	*/
		var skuId: Long? = null,
		/**
	* 满多少
	*/
		var fullPrice: BigDecimal? = null,
		/**
	* 减多少
	*/
		var reducePrice: BigDecimal? = null,
		/**
	* 是否参与其他优惠
	*/
		var addOther: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}