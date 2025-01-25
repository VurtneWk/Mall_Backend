package com.vurtnewk.mall.order.vo

import java.math.BigDecimal
import java.util.Date

/**
 * spu信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
data class SpuInfoVo(
    /**
     * 商品id
     */
    var id: Long? = null,
    /**
     * 商品名称
     */
    var spuName: String? = null,
    /**
     * 商品描述
     */
    var spuDescription: String? = null,
    /**
     * 所属分类id
     */
    var catalogId: Long? = null,
    /**
     * 品牌id
     */
    var brandId: Long? = null,
    /**
     * 重量
     */
    var weight: BigDecimal? = null,
    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    var publishStatus: Int? = null,
    /**
     * 创建时间
     */
    var createTime: Date? = null,
    /**
     * 更新时间
     */
    var updateTime: Date? = null,
)