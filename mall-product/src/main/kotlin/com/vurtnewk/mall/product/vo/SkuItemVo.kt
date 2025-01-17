package com.vurtnewk.mall.product.vo

import com.vurtnewk.mall.product.entity.SkuImagesEntity
import com.vurtnewk.mall.product.entity.SkuInfoEntity
import com.vurtnewk.mall.product.entity.SpuInfoDescEntity

/**
 * 商品详情sku信息
 * @author   vurtnewk
 * @since    2025/1/19 17:52
 */

/**
 * 需要获取的数据信息
 * 1. sku基本信息 pms_sku_info
 * 2. sku图片信息 pms_sku_images
 * 3. sku对应的spu销售属性组合
 * 4. sku介绍:spu数据 pms_spu_info
 * 5. sku规格参数信息
 */
data class SkuItemVo(
    /**
     * sku基本信息 pms_sku_info
     */
    var info: SkuInfoEntity? = null,
    /**
     * 是否有货
     */
    var hasStock: Boolean = true, //TODO
    /**
     * sku图片信息
     */
    var images: List<SkuImagesEntity> = emptyList(),
    /**
     * sku介绍:spu数据
     */
    var desc: SpuInfoDescEntity? = null,
    /**
     * sku对应的spu销售属性组合
     */
    var saleAttr: List<SkuItemSaleAttrVo> = listOf(),
    /**
     * sku规格参数信息
     */
    var groupAttrs: List<SpuItemAttrGroupVo> = listOf(),
)

data class SkuItemSaleAttrVo(
    /**
     * 属性id
     */
    var attrId: Long? = null,
    /**
     * 属性名
     */
    var attrName: String? = null,
//    var attrValues: String? = null,
    var attrValues: MutableList<AttrValueWithSkuIdVo> = mutableListOf(),
)

data class AttrValueWithSkuIdVo(val attrValue: String, val skuIds: String)

data class SpuItemAttrGroupVo(
    var groupName: String = "",
    var attrs: MutableList<SpuBaseAttrVo> = mutableListOf(),
)

data class SpuBaseAttrVo(
    /**
     * 属性名
     */
    var attrName: String = "",
    var attrValue: String = "",

    )


