package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.CategoryDao
import com.vurtnewk.mall.product.entity.CategoryEntity
import com.vurtnewk.mall.product.service.CategoryService


@Service("categoryService")
class CategoryServiceImpl : ServiceImpl<CategoryDao, CategoryEntity>() , CategoryService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CategoryEntity>().getPage(params),
            QueryWrapper<CategoryEntity>()
        )
        return PageUtils(page)
    }
}