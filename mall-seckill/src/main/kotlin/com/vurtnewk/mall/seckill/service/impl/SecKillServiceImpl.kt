package com.vurtnewk.mall.seckill.service.impl

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.vurtnewk.common.constants.CommonConstants
import com.vurtnewk.common.utils.ext.logError
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.seckill.feign.CouponFeignService
import com.vurtnewk.mall.seckill.feign.ProductFeignService
import com.vurtnewk.mall.seckill.interceptor.LoginUserInterceptor
import com.vurtnewk.mall.seckill.service.SecKillService
import com.vurtnewk.mall.seckill.to.SecKillSkuRedisDto
import com.vurtnewk.mall.seckill.vo.SecKillSessionsWithSkusVo
import com.vurtnewk.mall.seckill.vo.SkuInfoVo
import org.redisson.api.RedissonClient
import org.springframework.beans.BeanUtils
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

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

            logInfo("上架商品信息: $secKillSessionsWithSkusDto")
        }
    }

    fun saveSessionInfos(secKillSessionsWithSkusDto: List<SecKillSessionsWithSkusVo>) {
        secKillSessionsWithSkusDto.forEach { session ->
            val key = "${session.startTime!!.time}${CommonConstants.UNDERLINE}${session.endTime!!.time}"
            //如果没有这个 key 存在 ， 才进行保存
            val hasKey = redisTemplate.hasKey(getRedisKey(key))
            if (!hasKey && session.relationEntities.isNotEmpty()) {
                val skuIds = session.relationEntities.map { "${it.promotionSessionId}-${it.skuId}" }
                redisTemplate.opsForList().leftPushAll(getRedisKey(key), skuIds)
            }
        }
    }

    fun saveSessionSkuInfos(secKillSessionsWithSkusDto: List<SecKillSessionsWithSkusVo>) {
        secKillSessionsWithSkusDto.forEach { session ->
            val boundHashOps = redisTemplate.boundHashOps<String, String>(SKU_KILL_CACHE_PREFIX)
            session.relationEntities.forEach { secKillSku ->
                val redisKey = "${secKillSku.promotionSessionId}-${secKillSku.skuId}"
                // 检查是否已经存在
                if (boundHashOps.hasKey(redisKey) != true) {
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
                    boundHashOps.put(redisKey, jsonString)
                }
            }
        }
    }

    /**
     * 获取当前要上架的商品数据
     */
    override fun getCurrentSecKillSkus(): List<SecKillSkuRedisDto> {
        val now = Date().time
        // //获取所有指定key格式的列表
        val keys = redisTemplate.keys("$SESSIONS_CACHE_PREFIX*")
        val list = mutableListOf<SecKillSkuRedisDto>()
        keys.forEach { key ->
            //key的格式： seckill:sessions:1738332000000_1738335600000
            val splitTime = key.replace(SESSIONS_CACHE_PREFIX, "")
                .split(CommonConstants.UNDERLINE)
            if (now >= splitTime[0].toLong() && now <= splitTime[1].toLong()) {
                // 获取这个秒杀场次的需要的所有商品信息
                val range = redisTemplate.opsForList().range(key, -100, 100)
                // 存的具体商品信息
                val boundHashOps = redisTemplate.boundHashOps<String, String>(SKU_KILL_CACHE_PREFIX)
                range ?: return@forEach
                val skusJsonList = boundHashOps.multiGet(range)
                skusJsonList ?: return@forEach
                skusJsonList.forEach { itemJson ->
//                    secKillSkuRedisDto.randomCode = "" //当前秒杀是已经开始了 就可以带随机码
                    // ???? 教程中这里直接使用map收集，return了集合，上面的keys如果有多个是有问题的啊 ????
                    list.add(JSON.parseObject(itemJson, SecKillSkuRedisDto::class.java))
                }
            }
        }
        return list
    }

    override fun getSkuSecKillInfo(skuId: Long): SecKillSkuRedisDto? {
        val boundHashOps = redisTemplate.boundHashOps<String, String>(SKU_KILL_CACHE_PREFIX)
        val keys = boundHashOps.keys()
        if (!keys.isNullOrEmpty()) {
            val regx = "\\d-${skuId}"
            keys.forEach {
                if (Pattern.matches(regx, it)) {
                    val json = boundHashOps.get(it)
                    val secKillSkuRedisDto = JSON.parseObject(json, SecKillSkuRedisDto::class.java)
                    //随机码处理
                    val now = Date().time
                    // 当前时间不在秒杀时间的范围内
                    if (now <= secKillSkuRedisDto.startTime || now >= secKillSkuRedisDto.endTime) {
                        secKillSkuRedisDto.randomCode = null
                    }
                    return secKillSkuRedisDto
                }
            }
        }
        return null
    }

    override fun kill(killId: String, key: String, num: Int): String? {
        val memberRespVo = LoginUserInterceptor.loginUserThreadLocal.get()
        // 获取当前秒杀商品的详细信息
        val boundHashOps = redisTemplate.boundHashOps<String, String>(SKU_KILL_CACHE_PREFIX)
        val json = boundHashOps.get(killId)
        if (json.isNullOrEmpty()) return null
        val secKillSkuRedisDto = JSON.parseObject(json, SecKillSkuRedisDto::class.java)

        //region 校验合法性

        // 校验时间合法性 当前时间是属于还未开始 或者已大于结束时间 都不合法
        val currentTime = Date().time
        if (currentTime <= secKillSkuRedisDto.startTime || currentTime >= secKillSkuRedisDto.endTime) return null

        // 校验随机码
        //这个 redisKillId 实际和上面的boundHashOps.get(killId) 判断有重复，要是有问题 也代表存储时就有问题.
        val redisKillId = "${secKillSkuRedisDto.promotionSessionId}-${secKillSkuRedisDto.skuId}"
        if (key != secKillSkuRedisDto.randomCode || killId != redisKillId) return null

        // 校验数量
        if (num > (secKillSkuRedisDto.seckillLimit ?: 1)) return null

        // 校验是否已经买过了 幂等性
        // 如果只要秒杀成功，就去占位，
        val redisKey =
            "${memberRespVo.id}${CommonConstants.UNDERLINE}${secKillSkuRedisDto.promotionSessionId}${CommonConstants.UNDERLINE}${secKillSkuRedisDto.skuId}"
        val diffTime = secKillSkuRedisDto.endTime - currentTime
        // 如果占位成功，说明没买过 ; 占位失败，说明已经买过了
        val ifAbsent = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), diffTime, TimeUnit.MILLISECONDS)
        if (ifAbsent != true) return null

        //endregion

        //region 开始准备减redis里的库存
        val semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + key)
        try {
            val tryAcquire = semaphore.tryAcquire(num, Duration.ofMillis(100))
            //秒杀成功 快速下单
            if (tryAcquire) return IdWorker.getTimeId()
        } catch (e: Exception) {
            logError("尝试减 redis 库存失败: ${e.message}")
        }
        //endregion
        return null
    }
}