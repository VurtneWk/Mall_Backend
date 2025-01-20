package com.vurtnewk.mall.order.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 订单项信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:21
 */
@TableName("oms_order_item")
data class OrderItemEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* order_id
	*/
		var orderId: Long? = null,
		/**
	* order_sn
	*/
		var orderSn: String? = null,
		/**
	* spu_id
	*/
		var spuId: Long? = null,
		/**
	* spu_name
	*/
		var spuName: String? = null,
		/**
	* spu_pic
	*/
		var spuPic: String? = null,
		/**
	* 品牌
	*/
		var spuBrand: String? = null,
		/**
	* 商品分类id
	*/
		var categoryId: Long? = null,
		/**
	* 商品sku编号
	*/
		var skuId: Long? = null,
		/**
	* 商品sku名字
	*/
		var skuName: String? = null,
		/**
	* 商品sku图片
	*/
		var skuPic: String? = null,
		/**
	* 商品sku价格
	*/
		var skuPrice: BigDecimal? = null,
		/**
	* 商品购买的数量
	*/
		var skuQuantity: Int? = null,
		/**
	* 商品销售属性组合（JSON）
	*/
		var skuAttrsVals: String? = null,
		/**
	* 商品促销分解金额
	*/
		var promotionAmount: BigDecimal? = null,
		/**
	* 优惠券优惠分解金额
	*/
		var couponAmount: BigDecimal? = null,
		/**
	* 积分优惠分解金额
	*/
		var integrationAmount: BigDecimal? = null,
		/**
	* 该商品经过优惠后的分解金额
	*/
		var realAmount: BigDecimal? = null,
		/**
	* 赠送积分
	*/
		var giftIntegration: Int? = null,
		/**
	* 赠送成长值
	*/
		var giftGrowth: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}