package com.vurtnewk.mall.order.feign

import com.vurtnewk.mall.order.vo.MemberAddressVo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author   vurtnewk
 * @since    2025/1/23 20:32
 */
@FeignClient("mall-member")
interface MemberFeignService {

    @GetMapping("/member/memberreceiveaddress/{memberId}/addresses")
    fun getAddress(@PathVariable("memberId") memberId: Long): List<MemberAddressVo>
}