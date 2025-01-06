package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.CategoryBrandRelationEntity

/**
 * 品牌分类关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface CategoryBrandRelationService : IService<CategoryBrandRelationEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

