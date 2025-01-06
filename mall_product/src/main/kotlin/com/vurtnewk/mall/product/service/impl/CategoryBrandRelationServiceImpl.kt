package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.CategoryBrandRelationDao
import com.vurtnewk.mall.product.entity.CategoryBrandRelationEntity
import com.vurtnewk.mall.product.service.CategoryBrandRelationService


@Service("categoryBrandRelationService")
class CategoryBrandRelationServiceImpl : ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity>() , CategoryBrandRelationService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CategoryBrandRelationEntity>().getPage(params),
            QueryWrapper<CategoryBrandRelationEntity>()
        )
        return PageUtils(page)
    }
}