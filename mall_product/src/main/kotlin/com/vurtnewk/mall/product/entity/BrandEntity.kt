package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 品牌
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_brand")
data class BrandEntity(
		/**
	* 品牌id
	*/
		@TableId
		var brandId: Long? = null,
		/**
	* 品牌名
	*/
		var name: String? = null,
		/**
	* 品牌logo地址
	*/
		var logo: String? = null,
		/**
	* 介绍
	*/
		var descript: String? = null,
		/**
	* 显示状态[0-不显示；1-显示]
	*/
		var showStatus: Int? = null,
		/**
	* 检索首字母
	*/
		var firstLetter: String? = null,
		/**
	* 排序
	*/
		var sort: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}