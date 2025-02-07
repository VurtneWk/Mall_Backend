package com.vurtnewk.common.dto.mq

import java.math.BigDecimal

/**
 * 快速订单数据
 * @author   vurtnewk
 * @since    2025/2/7 11:15
 */
data class SecKillOrderDto(
    var orderSn: String = "",
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
    var seckillPrice: BigDecimal = BigDecimal.ZERO,
    /**
     * 购买数量
     */
    var num: Int = 0,
    /**
     * 会员ID
     */
    var memberId: Long? = null,
)