package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.math.BigDecimal
import java.io.Serializable
import java.util.Date

/**
 * sku信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@TableName("pms_sku_info")
data class SkuInfoEntity(
    /**
     * skuId
     */
    @TableId
    var skuId: Long? = null,
    /**
     * spuId
     */
    var spuId: Long? = null,
    /**
     * sku名称
     */
    var skuName: String? = null,
    /**
     * sku介绍描述
     */
    var skuDesc: String? = null,
    /**
     * 所属分类id
     */
    var catalogId: Long? = null,
    /**
     * 品牌id
     */
    var brandId: Long? = null,
    /**
     * 默认图片
     */
    var skuDefaultImg: String? = null,
    /**
     * 标题
     */
    var skuTitle: String? = null,
    /**
     * 副标题
     */
    var skuSubtitle: String? = null,
    /**
     * 价格
     */
    var price: BigDecimal? = null,
    /**
     * 销量
     */
    var saleCount: Long? = null,
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}