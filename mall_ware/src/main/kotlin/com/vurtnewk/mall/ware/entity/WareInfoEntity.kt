package com.vurtnewk.mall.ware.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 仓库信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@TableName("wms_ware_info")
data class WareInfoEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 仓库名
	*/
		var name: String? = null,
		/**
	* 仓库地址
	*/
		var address: String? = null,
		/**
	* 区域编码
	*/
		var areacode: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}