package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberStatisticsInfoDao
import com.vurtnewk.mall.member.entity.MemberStatisticsInfoEntity
import com.vurtnewk.mall.member.service.MemberStatisticsInfoService


@Service("memberStatisticsInfoService")
class MemberStatisticsInfoServiceImpl : ServiceImpl<MemberStatisticsInfoDao, MemberStatisticsInfoEntity>() , MemberStatisticsInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberStatisticsInfoEntity>().getPage(params),
            QueryWrapper<MemberStatisticsInfoEntity>()
        )
        return PageUtils(page)
    }
}