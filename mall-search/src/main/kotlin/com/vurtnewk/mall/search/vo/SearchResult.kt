package com.vurtnewk.mall.search.vo

import com.vurtnewk.common.dto.SkuEsModelDto

/**
 * 查询到的所有商品信息
 * @author   vurtnewk
 * @since    2025/1/18 03:05
 */
data class SearchResult(

    var products: List<SkuEsModelDto>? = null,
    //region 分页信息
    var pageNum: Int? = null,
    var total: Long = 0L,
    var totalPages: Int? = null,
    //endregion
    /**
     * 查询到的结果 涉及的所有品牌
     */
    var brands: List<BrandVo>? = null,

    /**
     * 查询到的结果 所有涉及的属性
     */
    var attrs: List<AttrVo>? = null,
    /**
     * 查询所涉及到的分类
     */
    var catalogs: List<CatalogVo>? = null,
    /**
     * 面包屑导航数据
     */
    var navs: MutableList<NavVo> = mutableListOf(),
) {
    data class BrandVo(
        var brandId: Long? = 0L,
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

    data class NavVo(
        var navName: String? = null,
        var navValue: String? = null,
        var link: String? = null,
    )
}


