package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.mall.product.dao.BrandDao

import com.vurtnewk.mall.product.dao.CategoryBrandRelationDao
import com.vurtnewk.mall.product.dao.CategoryDao
import com.vurtnewk.mall.product.entity.BrandEntity
import com.vurtnewk.mall.product.entity.CategoryBrandRelationEntity
import com.vurtnewk.mall.product.service.BrandService
import com.vurtnewk.mall.product.service.CategoryBrandRelationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy


@Service("categoryBrandRelationService")
class CategoryBrandRelationServiceImpl : ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity>(),
    CategoryBrandRelationService {

    @Autowired
    lateinit var mBrandDao: BrandDao

    @Autowired
    lateinit var mCategoryDao: CategoryDao

    @Autowired
    @Lazy //解决循环依赖 BrandServiceImpl 中也有 CategoryBrandRelationService
    lateinit var mBrandService: BrandService


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

    override fun getBrandsByCatId(catId: Long): List<BrandEntity> {
        // 先从 关系表中查询出所有指定 catId 的数据
        val idList = KtQueryChainWrapper(CategoryBrandRelationEntity::class.java)
            .eq(CategoryBrandRelationEntity::catelogId, catId)
            .list()
            .mapNotNull {// map 出所有的brandId
                it.brandId
            }
        //根据ID 批量查出所有数据
        return idList
            .takeIf { it.isNotEmpty() }
            ?.let {
                mBrandService.listByIds(idList)
            } ?: emptyList()
    }
}