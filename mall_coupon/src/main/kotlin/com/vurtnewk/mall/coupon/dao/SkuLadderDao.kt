package com.vurtnewk.mall.coupon.dao

import com.vurtnewk.mall.coupon.entity.SkuLadderEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 商品阶梯价格
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@Mapper
interface SkuLadderDao : BaseMapper<SkuLadderEntity> {
	
}
