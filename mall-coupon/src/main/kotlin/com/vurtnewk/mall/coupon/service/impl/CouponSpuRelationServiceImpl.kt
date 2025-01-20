package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.CouponSpuRelationDao
import com.vurtnewk.mall.coupon.entity.CouponSpuRelationEntity
import com.vurtnewk.mall.coupon.service.CouponSpuRelationService


@Service("couponSpuRelationService")
class CouponSpuRelationServiceImpl : ServiceImpl<CouponSpuRelationDao, CouponSpuRelationEntity>() , CouponSpuRelationService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CouponSpuRelationEntity>().getPage(params),
            QueryWrapper<CouponSpuRelationEntity>()
        )
        return PageUtils(page)
    }
}