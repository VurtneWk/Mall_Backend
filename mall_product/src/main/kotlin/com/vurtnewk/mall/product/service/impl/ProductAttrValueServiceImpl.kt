package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.ProductAttrValueDao
import com.vurtnewk.mall.product.entity.ProductAttrValueEntity
import com.vurtnewk.mall.product.service.ProductAttrValueService


@Service("productAttrValueService")
class ProductAttrValueServiceImpl : ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity>() , ProductAttrValueService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<ProductAttrValueEntity>().getPage(params),
            QueryWrapper<ProductAttrValueEntity>()
        )
        return PageUtils(page)
    }
}