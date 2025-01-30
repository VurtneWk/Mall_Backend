package com.vurtnewk.mall.seckill.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 00:31
 */
@FeignClient("mall-coupon")
interface CouponFeignService {

    @GetMapping("/coupon/seckillsession/latest3DaySession")
    fun getLatest3DaySession(): R
}