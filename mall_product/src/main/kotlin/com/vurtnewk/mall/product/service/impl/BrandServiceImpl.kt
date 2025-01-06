package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.BrandDao
import com.vurtnewk.mall.product.entity.BrandEntity
import com.vurtnewk.mall.product.service.BrandService


@Service("brandService")
class BrandServiceImpl : ServiceImpl<BrandDao, BrandEntity>() , BrandService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<BrandEntity>().getPage(params),
            QueryWrapper<BrandEntity>()
        )
        return PageUtils(page)
    }
}