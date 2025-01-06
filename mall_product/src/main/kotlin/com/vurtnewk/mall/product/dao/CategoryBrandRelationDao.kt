package com.vurtnewk.mall.product.dao

import com.vurtnewk.mall.product.entity.CategoryBrandRelationEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 品牌分类关联
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@Mapper
interface CategoryBrandRelationDao : BaseMapper<CategoryBrandRelationEntity> {
	
}
