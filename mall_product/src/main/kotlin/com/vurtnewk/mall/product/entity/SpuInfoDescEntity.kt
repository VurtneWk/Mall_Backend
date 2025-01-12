package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * spu信息介绍
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@TableName("pms_spu_info_desc")
data class SpuInfoDescEntity(
		/**
	* 商品id
	*/
		@TableId
		var spuId: Long? = null,
		/**
	* 商品介绍
	*/
		var decript: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}