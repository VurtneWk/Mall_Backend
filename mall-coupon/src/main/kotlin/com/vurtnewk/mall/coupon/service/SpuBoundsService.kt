package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.SpuBoundsEntity

/**
 * 商品spu积分设置
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface SpuBoundsService : IService<SpuBoundsEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

