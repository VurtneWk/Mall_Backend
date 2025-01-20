package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SeckillSessionDao
import com.vurtnewk.mall.coupon.entity.SeckillSessionEntity
import com.vurtnewk.mall.coupon.service.SeckillSessionService


@Service("seckillSessionService")
class SeckillSessionServiceImpl : ServiceImpl<SeckillSessionDao, SeckillSessionEntity>() , SeckillSessionService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SeckillSessionEntity>().getPage(params),
            QueryWrapper<SeckillSessionEntity>()
        )
        return PageUtils(page)
    }
}