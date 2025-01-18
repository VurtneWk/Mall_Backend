@file:Suppress("DEPRECATION")

package com.vurtnewk.mall.search.service.impl

import com.vurtnewk.mall.search.config.MallElasticSearchConfig
import com.vurtnewk.mall.search.constants.EsConstants
import com.vurtnewk.mall.search.service.MallSearchService
import com.vurtnewk.mall.search.vo.SearchParam
import com.vurtnewk.mall.search.vo.SearchResult
import org.apache.lucene.search.join.ScoreMode
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Service

/**
 *
 * @author   vurtnewk
 * @since    2025/1/18 02:41
 */
@Service
class MallSearchServiceImpl(
    private val mRestHighLevelClient: RestHighLevelClient,
) : MallSearchService {
    /**
     * ## 搜索页面主功能
     * 根据条件进行检索
     *
     * - DSL语句查看: 搜索商品的DSL.json
     */
    override fun search(param: SearchParam): SearchResult? {
        //1. 动态构建出查询需要的DSL语句
        kotlin.runCatching {
            //2. 执行检索请求
            mRestHighLevelClient.search(buildSearchRequest(param), MallElasticSearchConfig.COMMON_OPTIONS)
        }.onSuccess {
            //3. 分析响应数据 分钟成我们需要的格式
            return buildSearchResult(it)
        }.onFailure {
            return null
        }
        return null
    }


    /**
     * 封装构建 检索请求
     * 查看对比 搜索商品的DSL.json
     */
    private fun buildSearchRequest(param: SearchParam): SearchRequest {
        val searchSourceBuilder = SearchSourceBuilder()
        //region 模糊匹配、过滤(按照属性、分类、品牌、价格区间、库存)
        val boolQuery = QueryBuilders.boolQuery()
        // must 模糊匹配
        if (!param.keyword.isNullOrEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.keyword))
        }
        // filter
        param.catalog3Id?.let {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", it))
        }
        if (!param.attrs.isNullOrEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.attrs))
        }
        if (!param.attrs.isNullOrEmpty()) {
            param.attrs.forEach { attr ->
                //每一个遍历的条件都要单独组合成一个nested
                //不然会变成同一个 nested 里的 must 拼装 多个条件 ( 无法满足条件的 )
                val nestedBoolQuery = QueryBuilders.boolQuery()
                val split = attr.split("_")
                //attr的ID
                val attrId = split[0]
                //可能有多个值
                val attrValues = split[1].split(":")
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId))
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues))
                val nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None)
                boolQuery.filter(nestedQuery)
            }
        }
        param.hasStock?.let {
            boolQuery.filter(QueryBuilders.termQuery("hasStock", it))
        }
        param.skuPrice?.let { skuPrice ->
            val rangeQuery = QueryBuilders.rangeQuery("skuPrice")
            val split = skuPrice.split("_")
            when {
                skuPrice.startsWith("_") && split.size == 1 -> rangeQuery.gte(split[0])
                skuPrice.endsWith("_") && split.size == 1 -> rangeQuery.lte(split[0])
                split.size == 2 -> rangeQuery.gte(split[0]).lte(split[1])
                else -> {}
            }
            boolQuery.filter(rangeQuery)
        }
        //把前面构建的条件 都封装进最终的 searchSourceBuilder
        searchSourceBuilder.query(boolQuery)
        //endregion

        //region 排序、分页、高亮


        //endregion


        //region 聚合分析


        //endregion
        return SearchRequest(arrayOf(EsConstants.PRODUCT_INDEX), searchSourceBuilder)
    }

    /**
     * 封装查询结果
     */
    private fun buildSearchResult(searchResponse: SearchResponse): SearchResult {
        return SearchResult()
    }
}