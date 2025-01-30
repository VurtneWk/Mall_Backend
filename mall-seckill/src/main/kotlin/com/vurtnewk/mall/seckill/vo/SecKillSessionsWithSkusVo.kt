package com.vurtnewk.mall.seckill.vo

import java.math.BigDecimal
import java.util.*

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 01:00
 */
data class SecKillSessionsWithSkusVo(
    /**
     * id
     */
    var id: Long? = null,
    /**
     * 场次名称
     */
    var name: String? = null,
    /**
     * 每日开始时间
     */
    var startTime: Date? = null,
    /**
     * 每日结束时间
     */
    var endTime: Date? = null,
    /**
     * 启用状态
     */
    var status: Int? = null,
    /**
     * 创建时间
     */
    var createTime: Date? = null,
    /**
     * 关联的商品信息
     */
    var relationEntities: List<SeckillSkuVo>,
)

data class SeckillSkuVo(
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
)