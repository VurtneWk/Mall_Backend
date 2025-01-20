package com.vurtnewk.mall.coupon.dao

import com.vurtnewk.mall.coupon.entity.CouponSpuRelationEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 优惠券与产品关联
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@Mapper
interface CouponSpuRelationDao : BaseMapper<CouponSpuRelationEntity> {
	
}
