package com.vurtnewk.mall.seckill.service.impl

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import com.vurtnewk.mall.seckill.feign.CouponFeignService
import com.vurtnewk.mall.seckill.feign.ProductFeignService
import com.vurtnewk.mall.seckill.service.SecKillService
import com.vurtnewk.mall.seckill.to.SecKillSkuRedisDto
import com.vurtnewk.mall.seckill.vo.SecKillSessionsWithSkusVo
import com.vurtnewk.mall.seckill.vo.SkuInfoVo
import org.redisson.api.RedissonClient
import org.springframework.beans.BeanUtils
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.UUID

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 00:29
 */
@Service
class SecKillServiceImpl(
    private val couponFeignService: CouponFeignService,
    private val redisTemplate: StringRedisTemplate,
    private val productFeignService: ProductFeignService,
    private val redissonClient: RedissonClient,
) : SecKillService {

    companion object {
        private const val SESSIONS_CACHE_PREFIX = "seckill:sessions:"
        private const val SKU_KILL_CACHE_PREFIX = "seckill:skus"
        private const val SKU_STOCK_SEMAPHORE = "seckill:stock:"

        private fun getRedisKey(key: String) = "${SESSIONS_CACHE_PREFIX}$key"
    }

    override fun uploadSecKillSkuLatest3Days() {
        //1 、 扫描需要参与的秒杀的活动
        val r = couponFeignService.getLatest3DaySession()
        if (r.isSuccess()) {
            val secKillSessionsWithSkusDto = r.getData(object : TypeReference<List<SecKillSessionsWithSkusVo>>() {})
            secKillSessionsWithSkusDto ?: return
            // 上架商品
            // 1. 缓存活动信息
            saveSessionInfos(secKillSessionsWithSkusDto)
            // 2. 缓存活动的关联商品信息
            saveSessionSkuInfos(secKillSessionsWithSkusDto)
        }
    }

    fun saveSessionInfos(secKillSessionsWithSkusDto: List<SecKillSessionsWithSkusVo>) {
        secKillSessionsWithSkusDto.forEach { session ->
            val key = "${session.startTime!!.time}_${session.endTime!!.time}"
            val skuIds = session.relationEntities.map { it.skuId.toString() }
            redisTemplate.opsForList().leftPushAll(getRedisKey(key), skuIds)
        }
    }

    fun saveSessionSkuInfos(secKillSessionsWithSkusDto: List<SecKillSessionsWithSkusVo>) {
        secKillSessionsWithSkusDto.forEach { session ->
            val boundHashOps = redisTemplate.boundHashOps<String, String>(SKU_KILL_CACHE_PREFIX)
            session.relationEntities.forEach { secKillSku ->
                val secKillSkuRedisDto = SecKillSkuRedisDto()
                //1. sku的基本信息
                val r = productFeignService.getSkuInfo(secKillSku.skuId!!)
                if (r.isSuccess()) {
                    secKillSkuRedisDto.skuInfoVo = r.getData("skuInfo", object : TypeReference<SkuInfoVo>() {})
                }
                //2. sku的秒杀信息
                BeanUtils.copyProperties(secKillSku, secKillSkuRedisDto)


                //3. 当前商品的时间
                secKillSkuRedisDto.startTime = session.startTime!!.time
                secKillSkuRedisDto.endTime = session.endTime!!.time
                //4. 随机码: 作用: 保护作用，开抢时才暴露
                val token = UUID.randomUUID().toString().replace("-", "")
                secKillSkuRedisDto.randomCode = token
                // 引入分布式信号量 ：一个作用是限流
                val semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token)
                // 商品可以秒杀的数量作为信号量
                semaphore.trySetPermits(secKillSku.seckillCount!!.toInt())
                // 保存数据
                val jsonString = JSON.toJSONString(secKillSkuRedisDto)
                boundHashOps.put(secKillSku.skuId.toString(), jsonString)
            }
        }
    }
}