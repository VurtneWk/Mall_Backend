package com.vurtnewk.mall.member.dao

import com.vurtnewk.mall.member.entity.MemberLoginLogEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 会员登录记录
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@Mapper
interface MemberLoginLogDao : BaseMapper<MemberLoginLogEntity> {
	
}
