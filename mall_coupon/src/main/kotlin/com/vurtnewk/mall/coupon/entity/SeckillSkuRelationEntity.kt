package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 秒杀活动商品关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_seckill_sku_relation")
data class SeckillSkuRelationEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 活动id
	*/
		var promotionId: Long? = null,
		/**
	* 活动场次id
	*/
		var promotionSessionId: Long? = null,
		/**
	* 商品id
	*/
		var skuId: Long? = null,
		/**
	* 秒杀价格
	*/
		var seckillPrice: BigDecimal? = null,
		/**
	* 秒杀总量
	*/
		var seckillCount: BigDecimal? = null,
		/**
	* 每人限购数量
	*/
		var seckillLimit: BigDecimal? = null,
		/**
	* 排序
	*/
		var seckillSort: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}