package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SeckillSkuRelationDao
import com.vurtnewk.mall.coupon.entity.SeckillSkuRelationEntity
import com.vurtnewk.mall.coupon.service.SeckillSkuRelationService


@Service("seckillSkuRelationService")
class SeckillSkuRelationServiceImpl : ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity>() , SeckillSkuRelationService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SeckillSkuRelationEntity>().getPage(params),
            QueryWrapper<SeckillSkuRelationEntity>()
        )
        return PageUtils(page)
    }
}