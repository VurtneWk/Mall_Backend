package com.vurtnewk.mall.coupon.dao

import com.vurtnewk.mall.coupon.entity.HomeSubjectEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@Mapper
interface HomeSubjectDao : BaseMapper<HomeSubjectEntity> {
	
}
