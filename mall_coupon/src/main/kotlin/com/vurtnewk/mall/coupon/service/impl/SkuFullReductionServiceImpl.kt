package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SkuFullReductionDao
import com.vurtnewk.mall.coupon.entity.SkuFullReductionEntity
import com.vurtnewk.mall.coupon.service.SkuFullReductionService


@Service("skuFullReductionService")
class SkuFullReductionServiceImpl : ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity>() , SkuFullReductionService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuFullReductionEntity>().getPage(params),
            QueryWrapper<SkuFullReductionEntity>()
        )
        return PageUtils(page)
    }
}