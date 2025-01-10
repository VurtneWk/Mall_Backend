package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.CategoryEntity

/**
 * 商品三级分类
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface CategoryService : IService<CategoryEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils
    fun listWithTree(): List<CategoryEntity>
    fun removeMenuByIds(asList: List<Long>)
    fun findCatelogPath(catelogId: Long): List<Long>
}

