package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.IntegrationChangeHistoryDao
import com.vurtnewk.mall.member.entity.IntegrationChangeHistoryEntity
import com.vurtnewk.mall.member.service.IntegrationChangeHistoryService


@Service("integrationChangeHistoryService")
class IntegrationChangeHistoryServiceImpl : ServiceImpl<IntegrationChangeHistoryDao, IntegrationChangeHistoryEntity>() , IntegrationChangeHistoryService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<IntegrationChangeHistoryEntity>().getPage(params),
            QueryWrapper<IntegrationChangeHistoryEntity>()
        )
        return PageUtils(page)
    }
}