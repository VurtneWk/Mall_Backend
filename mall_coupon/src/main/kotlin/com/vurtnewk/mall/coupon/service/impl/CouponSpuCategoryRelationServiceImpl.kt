package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.CouponSpuCategoryRelationDao
import com.vurtnewk.mall.coupon.entity.CouponSpuCategoryRelationEntity
import com.vurtnewk.mall.coupon.service.CouponSpuCategoryRelationService


@Service("couponSpuCategoryRelationService")
class CouponSpuCategoryRelationServiceImpl : ServiceImpl<CouponSpuCategoryRelationDao, CouponSpuCategoryRelationEntity>() , CouponSpuCategoryRelationService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CouponSpuCategoryRelationEntity>().getPage(params),
            QueryWrapper<CouponSpuCategoryRelationEntity>()
        )
        return PageUtils(page)
    }
}