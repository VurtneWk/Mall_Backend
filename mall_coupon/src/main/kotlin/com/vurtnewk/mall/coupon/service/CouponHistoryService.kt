package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.CouponHistoryEntity

/**
 * 优惠券领取历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface CouponHistoryService : IService<CouponHistoryEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

