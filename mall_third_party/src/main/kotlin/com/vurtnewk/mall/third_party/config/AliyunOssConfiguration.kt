package com.vurtnewk.mall.third_party.config

import com.aliyun.oss.OSS
import com.aliyun.oss.OSSClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * createTime:  2025/1/7 21:58
 * author:      vurtnewk
 * description:
 */
@Configuration
@RefreshScope
class AliyunOssConfiguration {


    @Value("\${endpoint}")
    private lateinit var endpoint: String

    @Value("\${accessKeyId}")
    private lateinit var accessKeyId: String

    @Value("\${accessKeySecret}")
    private lateinit var accessKeySecret: String

    @Bean
    fun ossClient(): OSS {
        println("endpoint = $endpoint , accessKeyId = $accessKeyId , accessKeySecret = $accessKeySecret")
        return OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret)
    }
}