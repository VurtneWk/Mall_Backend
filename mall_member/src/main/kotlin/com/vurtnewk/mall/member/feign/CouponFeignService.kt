package com.vurtnewk.mall.member.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping

/**
 * createTime:  2025/1/6 18:57
 * author:      vurtnewk
 * description: 远程调用接口
 */

// 远程客户端
// mall_coupon 注册中心的名字
@FeignClient("mall-coupon")
interface CouponFeignService {

//    复制的coupon里的CouponController的方法签名
    @RequestMapping("/coupon/coupon/member/list")
    fun memberCoupons(): R
}