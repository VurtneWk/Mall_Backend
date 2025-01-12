package com.vurtnewk.common.dto

import java.math.BigDecimal

/**
 * SKU 折扣信息
 * @author   vurtnewk
 * @since    2025/1/12 17:48
 */
data class SkuReductionDto(
    var skuId: Long? = null,
    var reducePrice: BigDecimal? = null,
    var countStatus: Int? = null,
    var fullCount: Int? = null,
    var fullPrice: BigDecimal? = null,
    var discount: BigDecimal? = null,
    var priceStatus: Int? = null,
    var memberPrice: List<MemberPriceDto?>? = null,
) {
    data class MemberPriceDto(
        var id: Long? = null,
        var name: String? = null,
        var price: BigDecimal? = null
    )
}
