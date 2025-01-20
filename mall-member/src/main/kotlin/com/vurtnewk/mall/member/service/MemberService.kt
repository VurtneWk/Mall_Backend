package com.vurtnewk.mall.member.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.member.entity.MemberEntity
import com.vurtnewk.mall.member.excetion.PhoneExistException
import com.vurtnewk.mall.member.excetion.UsernameExistException
import com.vurtnewk.mall.member.vo.MemberRegisterVo
import kotlin.jvm.Throws

/**
 * 会员
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
interface MemberService : IService<MemberEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils
    fun register(memberRegisterVo: MemberRegisterVo)
    @Throws(PhoneExistException::class)
    fun checkPhoneUnique(phone: String)
    @Throws(UsernameExistException::class)
    fun checkUserNameUnique(userName: String)
}

