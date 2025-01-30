package com.vurtnewk.mall.seckill.feign

import org.springframework.cloud.openfeign.FeignClient

/**
 *
 * @author   vurtnewk
 * @since    2025/1/31 00:31
 */
@FeignClient("mall-coupon")
interface CouponFeignService {
}