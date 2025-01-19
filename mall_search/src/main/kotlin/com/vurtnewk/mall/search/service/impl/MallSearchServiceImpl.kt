@file:Suppress("DEPRECATION")

package com.vurtnewk.mall.search.service.impl

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import com.vurtnewk.common.dto.SkuEsModelDto
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.search.config.MallElasticSearchConfig
import com.vurtnewk.mall.search.constants.EsConstants
import com.vurtnewk.mall.search.feign.ProductFeignService
import com.vurtnewk.mall.search.service.MallSearchService
import com.vurtnewk.mall.search.vo.AttrRespVO
import com.vurtnewk.mall.search.vo.SearchParam
import com.vurtnewk.mall.search.vo.SearchResult
import org.apache.lucene.search.join.ScoreMode
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.elasticsearch.search.sort.SortOrder
import org.springframework.stereotype.Service
import java.net.URLEncoder

/**
 *
 * @author   vurtnewk
 * @since    2025/1/18 02:41
 */
@Service
class MallSearchServiceImpl(
    private val mRestHighLevelClient: RestHighLevelClient,
    private val mProductFeignService: ProductFeignService,
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
            return buildSearchResult(it, param)
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
        // 分类ID
        param.catalog3Id?.let {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", it))
        }
        // 品牌ID
        if (!param.brandId.isNullOrEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.brandId))
        }
        // 属性
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
        // 是否有库存
        param.hasStock?.let {
            boolQuery.filter(QueryBuilders.termQuery("hasStock", param.hasStock == 1))
        }
        // 价格区间
        if (!param.skuPrice.isNullOrEmpty()) {
            //这里不过滤的话 长度都是2，其中一个是空串 ，和 Java 有区别
            val split = param.skuPrice.split("_").filter { it.isNotEmpty() }
            if (split.isNotEmpty()) {
                val rangeQuery = QueryBuilders.rangeQuery("skuPrice")
                when {
                    param.skuPrice.startsWith("_") -> rangeQuery.to(split[0])
                    param.skuPrice.endsWith("_") -> rangeQuery.from(split[0])
                    split.size == 2 -> rangeQuery.gte(split[0]).lte(split[1])
                    else -> {}
                }
                boolQuery.filter(rangeQuery)
            }
        }
        //把前面构建的条件 都封装进最终的 searchSourceBuilder
        searchSourceBuilder.query(boolQuery)
        //endregion

        //region 排序、分页、高亮
        //排序
        param.sort?.let {
            val split = it.split("_")
            //参数1 ： 要排序的字段
            //参数2 ： 升序还是降序
            searchSourceBuilder.sort(
                split[0],
                if ("asc".equals(split[1], ignoreCase = true)) SortOrder.ASC else SortOrder.DESC
            )
        }
        //分页
        searchSourceBuilder.from((param.pageNum - 1) * EsConstants.PRODUCT_PAGE_SIZE)
        searchSourceBuilder.size(EsConstants.PRODUCT_PAGE_SIZE)
        //高亮 有模糊匹配的情况下才加
        if (!param.keyword.isNullOrEmpty()) {
            val highlightBuilder = HighlightBuilder()
            highlightBuilder.field("skuTitle")
            highlightBuilder.preTags("<b style='color:red'>")
            highlightBuilder.postTags("</b>")
            searchSourceBuilder.highlighter(highlightBuilder)
        }
        //endregion

        //region 聚合分析
        // 品牌聚合
        val brandAgg = AggregationBuilders.terms("brand_agg").field("brandId").size(50)
        // 品牌聚合子聚合
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1))
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1))
        searchSourceBuilder.aggregation(brandAgg)
        // 分类聚合
        val catalogAgg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20)
        catalogAgg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1))
        searchSourceBuilder.aggregation(catalogAgg)
        // 属性聚合 (嵌套聚合)
        val attrsAgg = AggregationBuilders.nested("attr_agg", "attrs")
        val attrIdAgg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId")
        // attrIdAgg 里还有两个子聚合
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1))
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50))
        // attrIdAgg 是 attrsAgg的自聚合
        attrsAgg.subAggregation(attrIdAgg)
        searchSourceBuilder.aggregation(attrsAgg)
        //endregion
        logInfo("构建的DSL： $searchSourceBuilder")
        return SearchRequest(arrayOf(EsConstants.PRODUCT_INDEX), searchSourceBuilder)
    }

    /**
     * 封装查询结果
     */
    private fun buildSearchResult(searchResponse: SearchResponse, param: SearchParam): SearchResult {
        val searchResult = SearchResult()
        val hits = searchResponse.hits //searchResponse.internalResponse.hits() 一样的
        //region 返回所有查询到的商品
        //这里面才是每个数据
        val products = mutableListOf<SkuEsModelDto>()
        hits.hits?.forEach { searchHit ->
            val skuEsModelDto = JSON.parseObject(searchHit.sourceAsString, SkuEsModelDto::class.java)
            if (!param.keyword.isNullOrEmpty()) {
                searchHit.highlightFields["skuTitle"]?.fragments?.get(0)?.let {
                    skuEsModelDto.skuTitle = it.string()
                }
            }
            products.add(skuEsModelDto)
        }
        searchResult.products = products
        //endregion

        //region 当前所有商品涉及的属性信息
        ////////// 这里的 ParsedLongTerms、还是ParsedStringTerms 和映射关系有关，也可以查断点的信息
        val attrs = mutableListOf<SearchResult.AttrVo>()
        searchResponse.aggregations?.get<ParsedNested>("attr_agg")
            ?.aggregations?.get<ParsedLongTerms>("attr_id_agg")
            ?.buckets?.forEach { bucket ->
                val attrVo = SearchResult.AttrVo()
                attrVo.attrId = bucket.keyAsNumber.toLong()
                attrVo.attrName = bucket.aggregations.get<ParsedStringTerms>("attr_name_agg").buckets[0].keyAsString
                attrVo.attrValue = bucket.aggregations.get<ParsedStringTerms>("attr_value_agg")
                    .buckets.map {
                        it.keyAsString
                    }
                attrs.add(attrVo)
            }
        searchResult.attrs = attrs
        //endregion

        //region 当前商品涉及的品牌信息
        val brands = mutableListOf<SearchResult.BrandVo>()
        searchResponse.aggregations
            ?.get<ParsedLongTerms>("brand_agg")
            ?.buckets?.forEach { bucket ->
                val brandVo = SearchResult.BrandVo()
                brandVo.brandId = bucket.key?.toString()?.toLong()
                brandVo.brandName = bucket.aggregations?.get<ParsedStringTerms>("brand_name_agg")?.buckets?.get(0)?.keyAsString.orEmpty()
                brandVo.brandImg = bucket.aggregations?.get<ParsedStringTerms>("brand_img_agg")?.buckets?.get(0)?.keyAsString.orEmpty()
                brands.add(brandVo)
            }
        searchResult.brands = brands
        //endregion

        //region 当前商品涉及的分类信息
        val catalogs = mutableListOf<SearchResult.CatalogVo>()
        val catalogAgg = searchResponse.aggregations?.get<ParsedLongTerms>("catalog_agg")
        catalogAgg?.buckets?.forEach { bucket ->
            val catalogVo = SearchResult.CatalogVo()
            catalogVo.catalogId = bucket.key.toString().toLong()
            catalogVo.catalogName = bucket.aggregations.get<ParsedStringTerms>("catalog_name_agg").buckets[0].key.toString()
            catalogs.add(catalogVo)
        }
        searchResult.catalogs = catalogs
        //endregion

        //region 其它信息
        // 总条数信息
        searchResult.total = hits?.totalHits?.value ?: 0L
        //计算总页数
        if (searchResult.total == 0L) {
            searchResult.totalPages = 0
        } else {
            // 如果不能整除 就是还有一页剩余
            val hasRemainder = (searchResult.total % EsConstants.PRODUCT_PAGE_SIZE).toInt() != 0
            // 总页面
            searchResult.totalPages = (searchResult.total / EsConstants.PRODUCT_PAGE_SIZE).toInt() + if (hasRemainder) 1 else 0
        }
        // 来源参数的页码
        searchResult.pageNum = param.pageNum
        //endregion

        //region 构建面包屑导航功能
        searchResult.navs = param.attrs?.map { attr ->
            val navVo = SearchResult.NavVo()
            val split = attr.split("_")
            navVo.navValue = split[1]
            val result = mProductFeignService.getAttrsInfo(split[0].toLong())
            if (result.isSuccess()) {
                navVo.navName = result.getData("attr", object : TypeReference<AttrRespVO>() {}).attrName
            } else {
                navVo.navName = split[0]
            }
            val encode = URLEncoder.encode(attr,"UTF-8")
                .replace("+","%20") //浏览器和java对空格处理的差异
            //有可能前面是 ?attrs= 开头的
            val replace = param.queryString.replace("&attrs=$encode","")
                .replace("attrs=$encode&","") // 可能是要替换 ?attrs=xxx&yyyy 里的attrs=xxx&
                .replace("attrs=$encode","")  // 上面两个都没匹配就直接替换掉 ?attrs=$encode
            navVo.link = "http://search.mall.com/list.html?$replace"
            navVo
        }
        //endregion


        logInfo("查询到的结果：$searchResult")
        return searchResult
    }
}