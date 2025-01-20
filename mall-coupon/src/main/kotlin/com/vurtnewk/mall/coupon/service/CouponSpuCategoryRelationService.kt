package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.CouponSpuCategoryRelationEntity

/**
 * 优惠券分类关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface CouponSpuCategoryRelationService : IService<CouponSpuCategoryRelationEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

