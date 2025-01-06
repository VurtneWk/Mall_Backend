package com.vurtnewk.mall.member.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.member.entity.MemberReceiveAddressEntity

/**
 * 会员收货地址
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
interface MemberReceiveAddressService : IService<MemberReceiveAddressEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

