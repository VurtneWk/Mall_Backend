package com.vurtnewk.mall.member.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
		import java.math.BigDecimal
	import java.io.Serializable
import java.util.Date

/**
 * 会员等级
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@TableName("ums_member_level")
data class MemberLevelEntity(
		/**
	* id
	*/
		@TableId
		var id: Long? = null,
		/**
	* 等级名称
	*/
		var name: String? = null,
		/**
	* 等级需要的成长值
	*/
		var growthPoint: Int? = null,
		/**
	* 是否为默认等级[0->不是；1->是]
	*/
		var defaultStatus: Int? = null,
		/**
	* 免运费标准
	*/
		var freeFreightPoint: BigDecimal? = null,
		/**
	* 每次评价获取的成长值
	*/
		var commentGrowthPoint: Int? = null,
		/**
	* 是否有免邮特权
	*/
		var priviledgeFreeFreight: Int? = null,
		/**
	* 是否有会员价格特权
	*/
		var priviledgeMemberPrice: Int? = null,
		/**
	* 是否有生日特权
	*/
		var priviledgeBirthday: Int? = null,
		/**
	* 备注
	*/
		var note: String? = null,
	) : Serializable {
	companion object {
		private const val serialVersionUID: Long = 1L
	}
}