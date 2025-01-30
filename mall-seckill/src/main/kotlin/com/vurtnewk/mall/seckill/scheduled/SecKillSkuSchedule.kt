package com.vurtnewk.mall.seckill.scheduled

import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.seckill.service.SecKillService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * 秒杀商品的定时上架
 * @author   vurtnewk
 * @since    2025/1/31 00:18
 *
 * 在服务器空闲时上架，比如每天晚上3点，上架最近三天需要秒杀的商品
 * - 当前00:00:00 - 23:59:59
 * - 明天00:00:00 - 23:59:59
 * - 后天00:00:00 - 23:59:59
 */

@Service
class SecKillSkuSchedule(
    private val secKillService: SecKillService
) {


//    @Scheduled(cron = "0 0 3 * * ?")
    @Scheduled(cron = "0 * * * * ?")
    fun uploadSecKillSkuLatest3Days(){
        //1. 重复上架无需处理
        logInfo("上架商品信息")
        secKillService.uploadSecKillSkuLatest3Days()
    }
}