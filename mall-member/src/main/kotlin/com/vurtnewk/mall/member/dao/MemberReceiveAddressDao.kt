package com.vurtnewk.mall.member.dao

import com.vurtnewk.mall.member.entity.MemberReceiveAddressEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 会员收货地址
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@Mapper
interface MemberReceiveAddressDao : BaseMapper<MemberReceiveAddressEntity> {
	
}
