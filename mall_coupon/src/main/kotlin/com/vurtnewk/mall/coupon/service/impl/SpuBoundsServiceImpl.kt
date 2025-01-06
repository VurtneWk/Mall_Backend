package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SpuBoundsDao
import com.vurtnewk.mall.coupon.entity.SpuBoundsEntity
import com.vurtnewk.mall.coupon.service.SpuBoundsService


@Service("spuBoundsService")
class SpuBoundsServiceImpl : ServiceImpl<SpuBoundsDao, SpuBoundsEntity>() , SpuBoundsService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SpuBoundsEntity>().getPage(params),
            QueryWrapper<SpuBoundsEntity>()
        )
        return PageUtils(page)
    }
}