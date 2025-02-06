package com.vurtnewk.mall.seckill.controller

import com.vurtnewk.common.utils.R
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.seckill.service.SecKillService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 21:32
 */
@RestController
class SecKillController(private val secKillService: SecKillService) {

    /**
     * 返回当前时间可以参与的秒杀商品信息
     */
    @GetMapping("/currentSecKillSkus")
    fun getCurrentSecKillSkus(): R {
        logInfo("getCurrentSecKillSkus")
        val list = secKillService.getCurrentSecKillSkus()
        return R.ok().putData(list)
    }
}