package com.vurtnewk.mall.member.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.member.entity.MemberLoginLogEntity

/**
 * 会员登录记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
interface MemberLoginLogService : IService<MemberLoginLogEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

