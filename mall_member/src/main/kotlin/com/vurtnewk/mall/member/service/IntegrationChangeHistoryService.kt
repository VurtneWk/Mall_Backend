package com.vurtnewk.mall.member.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.member.entity.IntegrationChangeHistoryEntity

/**
 * 积分变化历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
interface IntegrationChangeHistoryService : IService<IntegrationChangeHistoryEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

