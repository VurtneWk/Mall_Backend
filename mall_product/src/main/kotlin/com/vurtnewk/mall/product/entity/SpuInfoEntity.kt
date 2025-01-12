package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.math.BigDecimal
import java.io.Serializable
import java.util.Date

/**
 * spu信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@TableName("pms_spu_info")
data class SpuInfoEntity(
    /**
     * 商品id
     */
    @TableId
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
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}