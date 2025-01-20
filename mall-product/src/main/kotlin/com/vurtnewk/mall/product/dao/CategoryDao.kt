package com.vurtnewk.mall.product.dao

import com.vurtnewk.mall.product.entity.CategoryEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 商品三级分类
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@Mapper
interface CategoryDao : BaseMapper<CategoryEntity> {
	
}
