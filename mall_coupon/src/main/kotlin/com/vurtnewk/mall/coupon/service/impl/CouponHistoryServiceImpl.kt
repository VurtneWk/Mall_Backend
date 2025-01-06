package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.CouponHistoryDao
import com.vurtnewk.mall.coupon.entity.CouponHistoryEntity
import com.vurtnewk.mall.coupon.service.CouponHistoryService


@Service("couponHistoryService")
class CouponHistoryServiceImpl : ServiceImpl<CouponHistoryDao, CouponHistoryEntity>() , CouponHistoryService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CouponHistoryEntity>().getPage(params),
            QueryWrapper<CouponHistoryEntity>()
        )
        return PageUtils(page)
    }
}