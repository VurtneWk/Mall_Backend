package com.vurtnewk.mall.seckill.service

import com.vurtnewk.mall.seckill.to.SecKillSkuRedisDto

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 00:29
 */
interface SecKillService {
    fun uploadSecKillSkuLatest3Days()
    fun getCurrentSecKillSkus(): List<SecKillSkuRedisDto>
}