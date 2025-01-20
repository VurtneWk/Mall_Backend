package com.vurtnewk.mall.member.dao

import com.vurtnewk.mall.member.entity.IntegrationChangeHistoryEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 积分变化历史记录
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@Mapper
interface IntegrationChangeHistoryDao : BaseMapper<IntegrationChangeHistoryEntity> {
	
}
