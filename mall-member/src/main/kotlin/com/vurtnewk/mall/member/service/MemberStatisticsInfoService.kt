package com.vurtnewk.mall.member.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.member.entity.MemberStatisticsInfoEntity

/**
 * 会员统计信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
interface MemberStatisticsInfoService : IService<MemberStatisticsInfoEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

