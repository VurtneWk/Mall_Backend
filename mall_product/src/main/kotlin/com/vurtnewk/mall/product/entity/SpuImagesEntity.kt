package com.vurtnewk.mall.product.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.util.Date

/**
 * spu图片
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@TableName("pms_spu_images")
data class SpuImagesEntity(
    /**
     * id
     */
    @TableId
    var id: Long? = null,
    /**
     * spu_id
     */
    var spuId: Long? = null,
    /**
     * 图片名
     */
    var imgName: String? = null,
    /**
     * 图片地址
     */
    var imgUrl: String? = null,
    /**
     * 顺序
     */
    var imgSort: Int? = null,
    /**
     * 是否默认图
     */
    var defaultImg: Int? = null,
) : Serializable