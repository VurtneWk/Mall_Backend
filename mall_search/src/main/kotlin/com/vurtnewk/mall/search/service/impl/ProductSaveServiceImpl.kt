@file:Suppress("DEPRECATION")

package com.vurtnewk.mall.search.service.impl

import com.alibaba.fastjson2.JSON
import com.vurtnewk.common.dto.SkuEsModelDto
import com.vurtnewk.common.utils.ext.logError
import com.vurtnewk.mall.search.config.MallElasticSearchConfig
import com.vurtnewk.mall.search.constants.EsConstants
import com.vurtnewk.mall.search.service.ProductSaveService
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.xcontent.XContentType
import org.springframework.stereotype.Service

/**
 *
 * @author   vurtnewk
 * @since    2025/1/15 21:37
 */
@Service
class ProductSaveServiceImpl(
    private val restHighLevelClient: RestHighLevelClient,
) : ProductSaveService {


    override fun productStatusUp(skuEsModels: List<SkuEsModelDto>): Boolean {
        //  需要建立ES索引和映射关系 ps:通过kibana已操作完成
        val bulkRequest = BulkRequest()
        for (skuEsModel in skuEsModels) {
            val indexRequest = IndexRequest()
            //指定index
            indexRequest.index(EsConstants.PRODUCT_INDEX)
            //指定ID
            indexRequest.id(skuEsModel.skuId.toString())
            //具体的数据
            indexRequest.source(JSON.toJSONString(skuEsModel), XContentType.JSON)
            bulkRequest.add(indexRequest)
        }
        val response = restHighLevelClient.bulk(bulkRequest, MallElasticSearchConfig.COMMON_OPTIONS)
        if (response.hasFailures()) {
            logError("商品上架错误：${response.items.filter { it.isFailed }.map { it.id }}")
        }
        return response.hasFailures()

    }
}