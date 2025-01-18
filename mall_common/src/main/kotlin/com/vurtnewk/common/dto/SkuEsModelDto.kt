package com.vurtnewk.common.dto

import java.io.Serializable
import java.math.BigDecimal

/**
 * Sku在ES中保存的模型
 * @author   vurtnewk
 * @since    2025/1/15 14:03
 * ## 要传递的数据
 * ```json
 * PUT product
 * {
 *   "mappings": {
 *     "properties": {
 *       "skuId": {
 *         "type": "long"
 *       },
 *       "spuId": {
 *         "type": "keyword"
 *       },
 *       "skuTitle": {
 *         "type": "text",
 *         "analyzer": "ik_smart"
 *       },
 *       "skuPrice": {
 *         "type": "keyword"
 *       },
 *       "skuImg": {
 *         "type": "keyword",
 *         "index": false,
 *         "doc_values": false
 *       },
 *       "saleCount": {
 *         "type": "long"
 *       },
 *       "hasStock": {
 *         "type": "boolean"
 *       },
 *       "hotScore": {
 *         "type": "long"
 *       },
 *       "brandId": {
 *         "type": "long"
 *       },
 *       "catalogId": {
 *         "type": "long"
 *       },
 *       "brandName": {
 *         "type": "keyword",
 *         "index": false,
 *         "doc_values": false
 *       },
 *       "brandImg": {
 *         "type": "keyword",
 *         "index": false,
 *         "doc_values": false
 *       },
 *       "catalogName": {
 *         "type": "keyword",
 *         "index": false,
 *         "doc_values": false
 *       },
 *       "attrs": {
 *         "type": "nested",
 *         "properties": {
 *           "attrId": {
 *             "type": "long"
 *           },
 *           "attrName": {
 *             "type": "keyword",
 *             "index": false,
 *             "doc_values": false
 *           },
 *           "attrValue": {
 *             "type": "keyword"
 *           }
 *         }
 *       }
 *     }
 *   }
 * }
 * ```
 */
data class SkuEsModelDto(
    var skuId: Long = 0L,
    var spuId: Long = 0L,
    var skuTitle: String = "",
    var skuPrice: BigDecimal = BigDecimal.ZERO,
    var skuImg: String = "",
    var saleCount: Long = 0L,
    var hasStock: Boolean = false,
    var hotScore: Long = 0L,
    var brandId: Long = 0L,
    var catalogId: Long = 0L,
    var brandName: String = "",
    var brandImg: String = "",
    var catalogName: String = "",
    var attrs: List<AttrsEsModelDto> = emptyList(),
) : Serializable {
    data class AttrsEsModelDto(
        var attrId: Long = 0L,
        var attrName: String = "",
        var attrValue: String = "",
    ) : Serializable
}


