package com.vurtnewk.mall.search.vo

/**
 * 页面所有可能传递过来的搜索条件
 *
 * catalog3Id=225&keyword=小米&sort=saleCount_asc
 *
 * @author   vurtnewk
 * @since    2025/1/18 02:40
 */
data class SearchParam(
    /**
     * 关键字 全文匹配
     *
     * 匹配skuTitle属性
     */
    val keyword: String?,
    /**
     * 3级分类ID
     */
    val catalog3Id: Long?,
    /**
     * 排序条件
     *
     * 传参示例，6选1
     *
     * - saleCount_asc
     * - saleCount_desc
     * - skuPrice_asc
     * - skuPrice_desc
     * - hotScore_asc
     * - hotScore_desc
     */
    val sort: String?,

    /**
     * 过滤条件
     * - hasStock 是否有货 0无货/1有货
     */
    val hasStock: Int?,
    /**
     * 过滤条件
     * - skuPrice 价格区间
     *
     * 传参示例
     * - skuPrice=1_500 (1到500)
     * - skuPrice=_500 (最大500)
     * - skuPrice=500_ (最小500)
     */
    val skuPrice: String?,
    /**
     * 过滤条件
     * - brandId
     *
     * 传参示例
     * - brandId=1 (单个)
     * - brandId=1&brandId=2 (多个个)
     */
    val brandId: List<Long>?,
    /**
     * 过滤条件
     * - attrs
     *
     * 传参示例
     * - attrs=1_其他 (单个)
     * - attrs=1_其他:安卓 (多个)
     * - attrs=1_其他:安卓&attrs=2_5寸 (属性key也多个)
     */
    val attrs: List<String>?,
    /**
     * 页码
     */
    val pageNum: Int?,
)
