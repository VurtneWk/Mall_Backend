package com.vurtnewk.mall.product.vo

/**
 * 商品保存时提交的VO
 * @author   vurtnewk
 * @since    2025/1/12 01:48
 */
data class SpuInfoVO(
    var baseAttrs: List<BaseAttr?>? = null,
    var bounds: Bounds? = null,
    var brandId: Int? = null,
    var catalogId: Int? = null,
    var decript: List<String?>? = null,
    var images: List<String?>? = null,
    var publishStatus: Int? = null,
    var skus: List<Sku?>? = null,
    var spuDescription: String? = null,
    var spuName: String? = null,
    var weight: Double? = null
) {
    data class BaseAttr(
        var attrId: Int? = null, var attrValues: String? = null, var showDesc: Int? = null
    )

    data class Bounds(
        var buyBounds: Int? = null, var growBounds: Int? = null
    )

    data class Sku(
        var attr: List<Attr?>? = null,
        var countStatus: Int? = null,
        var descar: List<String?>? = null,
        var discount: Double? = null,
        var fullCount: Int? = null,
        var fullPrice: Int? = null,
        var images: List<Image?>? = null,
        var memberPrice: List<MemberPrice?>? = null,
        var price: String? = null,
        var priceStatus: Int? = null,
        var reducePrice: Int? = null,
        var skuName: String? = null,
        var skuSubtitle: String? = null,
        var skuTitle: String? = null
    ) {
        data class Attr(
            var attrId: Int? = null, var attrName: String? = null, var attrValue: String? = null
        )

        data class Image(
            var defaultImg: Int? = null, var imgUrl: String? = null
        )

        data class MemberPrice(
            var id: Int? = null, var name: String? = null, var price: Int? = null
        )
    }
}