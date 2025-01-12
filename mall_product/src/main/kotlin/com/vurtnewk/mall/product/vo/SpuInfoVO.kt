package com.vurtnewk.mall.product.vo

import com.vurtnewk.common.valid.ListIntValue
import jakarta.validation.constraints.*
import java.math.BigDecimal

/**
 * 商品保存时提交的VO
 * @author   vurtnewk
 * @since    2025/1/12 01:48
 */
data class SpuInfoVO(
    //region 基础数据
    @field:NotBlank(message = "商品名称不能为空")
    var spuName: String = "",

    @field:NotBlank(message = "商品描述不能为空")
    var spuDescription: String = "",

    @field:NotNull(message = "分类ID不能为空")
    @field:Positive(message = "请提供正确的分类ID")
//  使用 null 还是 默认值 ？如果有默认值 ， 然后前端并为传递这个字段时， 会使用默认值 ， 如果默认值满足了要求，不会报错
    var catalogId: Long = 0L,
//    var catalogId: Long? = null,

    @field:NotNull(message = "品牌ID不能为空")
    @field:Positive(message = "请提供正确的品牌ID")
    var brandId: Long = 0L,

    @field:NotNull(message = "重量不能为空")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "请输入正确的重量")
    var weight: BigDecimal = BigDecimal.ZERO,

    @field:NotNull(message = "请提供商品上架、下架状态")
    @field:ListIntValue(values = [0, 1], message = "只能是0下架，1上架")
    var publishStatus: Int = 0,

    //endregion

    //region 商品描述
    /**
     * 商品介绍时的图片
     */
    @field:NotEmpty(message = "请提供商品介绍图片")
    var decript: List<@NotNull @NotEmpty String> = emptyList(),

    //endregion

    //region 商品图集
    @field:NotEmpty(message = "请提供商品图集图片")
    var images: List<@NotNull @NotEmpty String> = emptyList(),
    //endregion

    //region spu的规格参数
    var baseAttrs: List<BaseAttr?>? = null,
    //endregion

    var skus: List<Sku?>? = null,

    var bounds: Bounds? = null
) {
    data class BaseAttr(
        var attrId: Long? = null,
        var attrValues: String? = null,
        /**
         * 是否快速展示
         */
        var showDesc: Int? = null
    )

    data class Bounds(
        var buyBounds: BigDecimal? = null,
        var growBounds: BigDecimal? = null
    )

    data class Sku(
        //region SkuInfoEntity中前端有传的数据
        var skuName: String? = null,
        var skuSubtitle: String? = null,
        var skuTitle: String? = null,
        var price: BigDecimal? = null,
        //endregion

        var reducePrice: BigDecimal? = null,
        var attr: List<Attr?>? = null,
        var countStatus: Int? = null,
        var descar: List<String?>? = null,
        var discount: BigDecimal? = null,
        var fullCount: Int? = null,
        var fullPrice: BigDecimal? = null,
        var images: List<Image?>? = null,
        var memberPrice: List<MemberPrice?>? = null,
        var priceStatus: Int? = null

    ) {
        data class Attr(
            var attrId: Long? = null,
            var attrName: String? = null,
            var attrValue: String? = null
        )

        data class Image(
            var defaultImg: Int? = null,
            var imgUrl: String? = null
        )

        data class MemberPrice(
            var id: Long? = null,
            var name: String? = null,
            var price: BigDecimal? = null
        )
    }
}