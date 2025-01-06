package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SeckillPromotionDao
import com.vurtnewk.mall.coupon.entity.SeckillPromotionEntity
import com.vurtnewk.mall.coupon.service.SeckillPromotionService


@Service("seckillPromotionService")
class SeckillPromotionServiceImpl : ServiceImpl<SeckillPromotionDao, SeckillPromotionEntity>() , SeckillPromotionService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SeckillPromotionEntity>().getPage(params),
            QueryWrapper<SeckillPromotionEntity>()
        )
        return PageUtils(page)
    }
}