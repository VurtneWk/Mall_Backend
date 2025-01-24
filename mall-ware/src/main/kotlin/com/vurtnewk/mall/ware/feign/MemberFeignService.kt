package com.vurtnewk.mall.ware.feign

import com.vurtnewk.common.utils.R
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 *
 * @author   vurtnewk
 * @since    2025/1/24 15:16
 */
@FeignClient("mall-member")
interface MemberFeignService {

    @RequestMapping("/member/memberreceiveaddress/info/{id}")
    fun addrInfo(@PathVariable("id") id: Long): R
}