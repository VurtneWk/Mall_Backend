package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.SkuSaleAttrValueDao
import com.vurtnewk.mall.product.entity.SkuSaleAttrValueEntity
import com.vurtnewk.mall.product.service.SkuSaleAttrValueService


@Service("skuSaleAttrValueService")
class SkuSaleAttrValueServiceImpl : ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity>() , SkuSaleAttrValueService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuSaleAttrValueEntity>().getPage(params),
            QueryWrapper<SkuSaleAttrValueEntity>()
        )
        return PageUtils(page)
    }
}