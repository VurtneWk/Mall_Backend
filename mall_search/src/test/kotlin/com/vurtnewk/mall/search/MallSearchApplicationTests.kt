package com.vurtnewk.mall.search

import com.vurtnewk.common.utils.ext.logInfo
import org.elasticsearch.client.RestHighLevelClient
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

}
