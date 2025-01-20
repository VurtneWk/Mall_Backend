package com.vurtnewk.mall.product.vo

import java.io.Serializable

/**
 *
 * @author   vurtnewk
 * @since    2025/1/16 12:54
 */
data class Catalog2Vo(
    /**
     * 1级父分类ID
     */
    var catalog1Id: String = "",
    /**
     * 三级子分类
     */
    var catalog3List: List<Catalog3Vo> = emptyList(),
    /**
     * 二级分类ID
     */
    var id: String = "",
    /**
     * 二级分类名
     */
    var name: String = "",
) : Serializable {


    data class Catalog3Vo(
        /**
         * 二级分类ID
         */
        var catalog2Id: String = "",
        /**
         * 三级分类ID
         */
        var id: String = "",
        /**
         *三级分类名
         */
        var name: String = "",
    ): Serializable

}
