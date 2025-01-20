package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberLevelDao
import com.vurtnewk.mall.member.entity.MemberLevelEntity
import com.vurtnewk.mall.member.service.MemberLevelService


@Service("memberLevelService")
class MemberLevelServiceImpl : ServiceImpl<MemberLevelDao, MemberLevelEntity>() , MemberLevelService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberLevelEntity>().getPage(params),
            QueryWrapper<MemberLevelEntity>()
        )
        return PageUtils(page)
    }
}