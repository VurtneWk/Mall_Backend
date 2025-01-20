package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.GrowthChangeHistoryDao
import com.vurtnewk.mall.member.entity.GrowthChangeHistoryEntity
import com.vurtnewk.mall.member.service.GrowthChangeHistoryService


@Service("growthChangeHistoryService")
class GrowthChangeHistoryServiceImpl : ServiceImpl<GrowthChangeHistoryDao, GrowthChangeHistoryEntity>() , GrowthChangeHistoryService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<GrowthChangeHistoryEntity>().getPage(params),
            QueryWrapper<GrowthChangeHistoryEntity>()
        )
        return PageUtils(page)
    }
}