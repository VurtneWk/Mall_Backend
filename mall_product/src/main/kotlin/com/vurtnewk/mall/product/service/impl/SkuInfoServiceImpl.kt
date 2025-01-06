package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.SkuInfoDao
import com.vurtnewk.mall.product.entity.SkuInfoEntity
import com.vurtnewk.mall.product.service.SkuInfoService


@Service("skuInfoService")
class SkuInfoServiceImpl : ServiceImpl<SkuInfoDao, SkuInfoEntity>() , SkuInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuInfoEntity>().getPage(params),
            QueryWrapper<SkuInfoEntity>()
        )
        return PageUtils(page)
    }
}