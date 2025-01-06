package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.SeckillSkuNoticeEntity

/**
 * 秒杀商品通知订阅
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface SeckillSkuNoticeService : IService<SeckillSkuNoticeEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

