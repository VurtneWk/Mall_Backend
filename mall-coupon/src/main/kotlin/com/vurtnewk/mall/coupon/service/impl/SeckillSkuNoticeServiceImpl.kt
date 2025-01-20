package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SeckillSkuNoticeDao
import com.vurtnewk.mall.coupon.entity.SeckillSkuNoticeEntity
import com.vurtnewk.mall.coupon.service.SeckillSkuNoticeService


@Service("seckillSkuNoticeService")
class SeckillSkuNoticeServiceImpl : ServiceImpl<SeckillSkuNoticeDao, SeckillSkuNoticeEntity>() , SeckillSkuNoticeService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SeckillSkuNoticeEntity>().getPage(params),
            QueryWrapper<SeckillSkuNoticeEntity>()
        )
        return PageUtils(page)
    }
}