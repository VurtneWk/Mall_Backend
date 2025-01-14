@file:Suppress("DEPRECATION")

package com.vurtnewk.mall.search.config

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * es的配置文件
 * @author   vurtnewk
 * @since    2025/1/14 21:05
 */
@Configuration
class MallElasticSearchConfig {

    @Bean
    fun client(): RestHighLevelClient {
        return RestHighLevelClient(RestClient.builder(HttpHost("127.0.0.1", 9200, "http")))
    }

}