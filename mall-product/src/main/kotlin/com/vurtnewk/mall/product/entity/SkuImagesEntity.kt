package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.util.Date

/**
 * sku图片
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@TableName("pms_sku_images")
data class SkuImagesEntity(
    /**
     * id
     */
    @TableId
    var id: Long? = null,
    /**
     * sku_id
     */
    var skuId: Long? = null,
    /**
     * 图片地址
     */
    var imgUrl: String? = null,
    /**
     * 排序
     */
    var imgSort: Int? = null,
    /**
     * 默认图[0 - 不是默认图，1 - 是默认图]
     */
    var defaultImg: Int? = null,
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}