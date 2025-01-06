package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.MemberPriceEntity

/**
 * 商品会员价格
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface MemberPriceService : IService<MemberPriceEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

