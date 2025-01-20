package com.vurtnewk.mall.auth.feign

import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.auth.vo.UserLoginVo
import com.vurtnewk.mall.auth.vo.UserRegisterVo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * 用户模块
 * @author   vurtnewk
 * @since    2025/1/21 03:27
 */
@FeignClient("mall-member")
interface MemberFeignService {

    @PostMapping("/member/member/register")
    fun register(@RequestBody userRegisterVo: UserRegisterVo): R

    @PostMapping("/member/member/login")
    fun login(@RequestBody memberLoginVo: UserLoginVo): R
}