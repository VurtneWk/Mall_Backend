package com.vurtnewk.mall.search.vo

import com.vurtnewk.common.dto.SkuEsModelDto

/**
 * 查询到的所有商品信息
 * @author   vurtnewk
 * @since    2025/1/18 03:05
 */
data class SearchResult(

    val products: List<SkuEsModelDto>,
    //region 分页信息
    val pageNum: Int,
    val total: Long,
    val totalPages: Int,
    //endregion
    /**
     * 查询到的结果 涉及的所有品牌
     */
    val brands: List<BrandVo>,

    /**
     * 查询到的结果 所有涉及的属性
     */
    val attrs: List<AttrVo>,
    /**
     * 查询所涉及到的分类
     */
    val catalogs: List<CatalogVo>,
) {
    data class BrandVo(
        var brandId: Long = 0L,
        var brandName: String = "",
        var brandImg: String = "",
    )

    data class AttrVo(
        var attrId: Long = 0L,
        var attrName: String = "",
        var attrValue: List<String> = emptyList(),
    )

    data class CatalogVo(
        var catalogId: Long = 0L,
        var catalogName: String = "",
    )
}


