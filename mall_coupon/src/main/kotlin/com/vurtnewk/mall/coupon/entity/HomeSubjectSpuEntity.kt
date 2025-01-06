package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
	import java.io.Serializable
import java.util.Date

/**
 * 专题商品
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_home_subject_spu")
data class HomeSubjectSpuEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 专题名字
	*/
		var name: String? = null,
		/**
	* 专题id
	*/
		var subjectId: Long? = null,
		/**
	* spu_id
	*/
		var spuId: Long? = null,
		/**
	* 排序
	*/
		var sort: Int? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}