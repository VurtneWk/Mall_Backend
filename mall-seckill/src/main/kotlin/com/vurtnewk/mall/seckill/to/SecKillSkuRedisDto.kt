package com.vurtnewk.mall.seckill.to

import com.vurtnewk.mall.seckill.vo.SkuInfoVo
import java.math.BigDecimal

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 01:48
 */
data class SecKillSkuRedisDto(
    var id: Long? = null,
    /**
     * 活动id
     */
    var promotionId: Long? = null,
    /**
     * 活动场次id
     */
    var promotionSessionId: Long? = null,
    /**
     * 商品id
     */
    var skuId: Long? = null,
    /**
     * 秒杀价格
     */
    var seckillPrice: BigDecimal? = null,
    /**
     * 秒杀总量
     */
    var seckillCount: BigDecimal? = null,
    /**
     * 每人限购数量
     */
    var seckillLimit: BigDecimal? = null,
    /**
     * 排序
     */
    var seckillSort: Int? = null,
    /**
     * sku的详细信息
     */
    var skuInfoVo: SkuInfoVo? = null,

    var startTime: Long = 0L,
    var endTime: Long = 0L,

    /**
     * 当前商品的秒杀随机码
     */
    var randomCode: String? = null,
)
