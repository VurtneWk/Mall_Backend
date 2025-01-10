package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.pageUtils

import com.vurtnewk.mall.product.dao.BrandDao
import com.vurtnewk.mall.product.entity.BrandEntity
import com.vurtnewk.mall.product.service.BrandService
import com.vurtnewk.mall.product.service.CategoryBrandRelationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional


@Service("brandService")
class BrandServiceImpl : ServiceImpl<BrandDao, BrandEntity>(), BrandService {

    @Autowired
    private lateinit var mCategoryBrandRelationService: CategoryBrandRelationService

    override fun queryPage(params: Map<String, Any>): PageUtils {
//        val page = this.page(
//            Query<BrandEntity>().getPage(params),
//            QueryWrapper<BrandEntity>()
//        )


        //模糊查询
        val key = params["key"] as? String?
        return KtQueryChainWrapper(BrandEntity::class.java).apply {
            if (!key.isNullOrBlank()) {
                eq(BrandEntity::brandId, key)
                or()
                like(BrandEntity::name, key)
            }
        }
            .page(Query<BrandEntity>().getPage(params))
            .pageUtils()

//        KtQueryChainWrapper(BrandEntity::class.java)
//            .eq(!key.isNullOrBlank(), BrandEntity::brandId, key)
//            .or()
//            .like(!key.isNullOrBlank(), BrandEntity::name, key)
//            .page(Query<BrandEntity>().getPage(params))
//            .pageUtils()
    }

    @Transactional
    override fun updateCascade(brand: BrandEntity) {
        this.updateById(brand) //更改自己
        if (!brand.name.isNullOrEmpty()) {
            //需要更新关联表中的数据
            mCategoryBrandRelationService.updateBrandName(brand.brandId!!, brand.name!!)
        }
    }
}