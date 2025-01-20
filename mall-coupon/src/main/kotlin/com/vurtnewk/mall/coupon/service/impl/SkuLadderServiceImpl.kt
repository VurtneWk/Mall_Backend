package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SkuLadderDao
import com.vurtnewk.mall.coupon.entity.SkuLadderEntity
import com.vurtnewk.mall.coupon.service.SkuLadderService


@Service("skuLadderService")
class SkuLadderServiceImpl : ServiceImpl<SkuLadderDao, SkuLadderEntity>() , SkuLadderService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuLadderEntity>().getPage(params),
            QueryWrapper<SkuLadderEntity>()
        )
        return PageUtils(page)
    }
}