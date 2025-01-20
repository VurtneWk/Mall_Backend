package com.vurtnewk.mall.coupon.dao

import com.vurtnewk.mall.coupon.entity.SeckillSessionEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 秒杀活动场次
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@Mapper
interface SeckillSessionDao : BaseMapper<SeckillSessionEntity> {
	
}
