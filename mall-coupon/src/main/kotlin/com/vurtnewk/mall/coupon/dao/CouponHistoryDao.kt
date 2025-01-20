package com.vurtnewk.mall.coupon.dao

import com.vurtnewk.mall.coupon.entity.CouponHistoryEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 优惠券领取历史记录
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@Mapper
interface CouponHistoryDao : BaseMapper<CouponHistoryEntity> {
	
}
