package com.vurtnewk.mall.product.config

import org.springframework.boot.autoconfigure.cache.CacheProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * 缓存配置类
 * @author   vurtnewk
 * @since    2025/1/17 22:32
 */
//CacheProperties 不在容器中，下面这句是让CacheProperties生效
@EnableConfigurationProperties(CacheProperties::class)
@Configuration
@EnableCaching
class MyCacheConfig {

    /**
     * 如果自己设置了
     */
    @Bean
    fun redisCacheConfiguration(mCacheProperties: CacheProperties): RedisCacheConfiguration {
        var config = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))
        //源码中，如果有自定义的 RedisCacheConfiguration ， 配置文件中的各个属性将失效
        // 将配置文件中的所有配置生效
        val redisProperties = mCacheProperties.redis
        if (redisProperties.timeToLive != null) {
            config = config.entryTtl(redisProperties.timeToLive)
        }
        if (redisProperties.keyPrefix != null) {
            config = config.prefixCacheNameWith(redisProperties.keyPrefix)
        }
        if (!redisProperties.isCacheNullValues) {
            config = config.disableCachingNullValues()
        }
        if (!redisProperties.isUseKeyPrefix) {
            config = config.disableKeyPrefix()
        }
        return config
    }
}