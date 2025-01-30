package com.vurtnewk.mall.coupon.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.coupon.entity.SeckillSessionEntity

/**
 * 秒杀活动场次
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
interface SeckillSessionService : IService<SeckillSessionEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun getLatest3DaySession(): List<SeckillSessionEntity>
}

