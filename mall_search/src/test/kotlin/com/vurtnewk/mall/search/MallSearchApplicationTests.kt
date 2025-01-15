package com.vurtnewk.mall.search

import com.alibaba.fastjson2.JSON
import com.vurtnewk.common.utils.ext.logDebug
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.search.config.MallElasticSearchConfig
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.xcontent.XContentType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MallSearchApplicationTests {

    @Autowired
    lateinit var client: RestHighLevelClient


    @Test
    fun contextLoads() {
        logInfo("client = $client")
//        println("contextLoads")
    }

    data class User(
        val userName: String? = null,
        val gender: String? = null,
        val age: Int? = null,
    )

    /**
     * ```tex
     * account_number : 472
     * balance : 25571
     * firstname : Lee
     * lastname : Long
     * age : 32
     * gender : F
     * address : 288 Mill Street
     * employer : Comverges
     * email : leelong@comverges.com
     * city : Movico
     * state : MT
     * ```
     */
    data class Account(
        var account_number: Int = 0,
        var balance: Int = 0,
        var firstname: String? = null,
        var lastname: String? = null,
        var age: Int = 0,
        var gender: String? = null,
        var address: String? = null,
        var employer: String? = null,
        var email: String? = null,
        var city: String? = null,
        var state: String? = null,
    )

    @Test
    fun indexData() {
        val indexRequest = IndexRequest("users")
        indexRequest.id("1")
        val user = User("zhangsan", "男", 20)
        val jsonString = JSON.toJSONString(user)
        indexRequest.source(jsonString, XContentType.JSON)
        val index = client.index(indexRequest, MallElasticSearchConfig.COMMON_OPTIONS)
        logInfo("响应数据 => $index")
    }

    @Test
    fun searchData() {
        val searchRequest = SearchRequest()
        searchRequest.indices("bank") //索引

        val searchSourceBuilder = SearchSourceBuilder()
//        searchSourceBuilder.sort("account_number", SortOrder.ASC);
//        searchSourceBuilder.from(5);
//        searchSourceBuilder.size(10);

        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"))
        //聚合查询
        val termsAggregationBuilder = AggregationBuilders.terms("ageAgg").field("age").size(10)
        searchSourceBuilder.aggregation(termsAggregationBuilder)

        val balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance")
        searchSourceBuilder.aggregation(balanceAvg)

        searchRequest.source(searchSourceBuilder) //DSL

        logDebug("查询条件 => , $searchSourceBuilder")

        val search: SearchResponse = client.search(searchRequest, RequestOptions.DEFAULT)
        logDebug("结果=> $search")
        val hits = search.hits.hits
        for (hit in hits) {
            logDebug("account: => ${JSON.parseObject(hit.sourceAsString, Account::class.java)}")
        }

    }

}
