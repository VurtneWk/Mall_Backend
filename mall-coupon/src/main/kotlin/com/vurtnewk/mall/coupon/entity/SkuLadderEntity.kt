package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 商品阶梯价格
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_sku_ladder")
data class SkuLadderEntity(
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
	* 满几件
	*/
		var fullCount: Int? = null,
		/**
	* 打几折
	*/
		var discount: BigDecimal? = null,
		/**
	* 折后价
	*/
		var price: BigDecimal? = null,
		/**
	* 是否叠加其他优惠[0-不可叠加，1-可叠加]
	*/
		var addOther: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}