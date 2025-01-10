package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.mall.product.dao.BrandDao

import com.vurtnewk.mall.product.dao.CategoryBrandRelationDao
import com.vurtnewk.mall.product.dao.CategoryDao
import com.vurtnewk.mall.product.entity.CategoryBrandRelationEntity
import com.vurtnewk.mall.product.service.BrandService
import com.vurtnewk.mall.product.service.CategoryBrandRelationService
import com.vurtnewk.mall.product.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired


@Service("categoryBrandRelationService")
class CategoryBrandRelationServiceImpl : ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity>(),
    CategoryBrandRelationService {

    @Autowired
    lateinit var mBrandDao: BrandDao

    @Autowired
    lateinit var mCategoryDao: CategoryDao


    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CategoryBrandRelationEntity>().getPage(params),
            QueryWrapper<CategoryBrandRelationEntity>()
        )
        return PageUtils(page)
    }

    override fun saveDetail(categoryBrandRelation: CategoryBrandRelationEntity) {
        categoryBrandRelation.brandName = mBrandDao.selectById(categoryBrandRelation.brandId).name
        categoryBrandRelation.catelogName = mCategoryDao.selectById(categoryBrandRelation.catelogId).name
        this.save(categoryBrandRelation)
    }

    override fun updateBrandName(brandId: Long, name: String) {
        val categoryBrandRelationEntity = CategoryBrandRelationEntity(brandName = name)
        KtUpdateChainWrapper(CategoryBrandRelationEntity::class.java)
            .eq(CategoryBrandRelationEntity::brandId, brandId)
            .update(categoryBrandRelationEntity)
    }
}