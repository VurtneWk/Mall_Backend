package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.CouponSpuRelationEntity

/**
 * 优惠券与产品关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface CouponSpuRelationService : IService<CouponSpuRelationEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

