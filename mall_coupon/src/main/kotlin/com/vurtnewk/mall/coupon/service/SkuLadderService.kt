package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.SkuLadderEntity

/**
 * 商品阶梯价格
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface SkuLadderService : IService<SkuLadderEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

