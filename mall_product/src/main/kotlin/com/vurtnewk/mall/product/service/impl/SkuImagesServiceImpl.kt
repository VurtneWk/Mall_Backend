package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.SkuImagesDao
import com.vurtnewk.mall.product.entity.SkuImagesEntity
import com.vurtnewk.mall.product.service.SkuImagesService


@Service("skuImagesService")
class SkuImagesServiceImpl : ServiceImpl<SkuImagesDao, SkuImagesEntity>() , SkuImagesService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuImagesEntity>().getPage(params),
            QueryWrapper<SkuImagesEntity>()
        )
        return PageUtils(page)
    }
}