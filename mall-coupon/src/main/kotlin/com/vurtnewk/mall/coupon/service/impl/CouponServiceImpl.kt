package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.CouponDao
import com.vurtnewk.mall.coupon.entity.CouponEntity
import com.vurtnewk.mall.coupon.service.CouponService


@Service("couponService")
class CouponServiceImpl : ServiceImpl<CouponDao, CouponEntity>() , CouponService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CouponEntity>().getPage(params),
            QueryWrapper<CouponEntity>()
        )
        return PageUtils(page)
    }
}