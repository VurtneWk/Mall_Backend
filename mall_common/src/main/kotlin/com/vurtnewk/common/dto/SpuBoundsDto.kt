package com.vurtnewk.common.dto

import java.io.Serializable
import java.math.BigDecimal

/**
 *
 * @author   vurtnewk
 * @since    2025/1/12 17:36
 */
data class SpuBoundsDto(
    var spuId: Long? = null,
    /**
     * 成长积分
     */
    var growBounds: BigDecimal? = null,
    /**
     * 购物积分
     */
    var buyBounds: BigDecimal? = null,
) : Serializable
