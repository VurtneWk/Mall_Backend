package com.vurtnewk.mall.ware.vo

import java.math.BigDecimal

/**
 *
 * @author   vurtnewk
 * @since    2025/1/24 15:56
 */
data class FareVo(
    var address: MemberAddressVo? = null,
    var fare: BigDecimal = BigDecimal.ZERO,
)
