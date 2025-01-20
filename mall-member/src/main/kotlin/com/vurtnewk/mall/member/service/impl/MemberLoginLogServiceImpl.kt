package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberLoginLogDao
import com.vurtnewk.mall.member.entity.MemberLoginLogEntity
import com.vurtnewk.mall.member.service.MemberLoginLogService


@Service("memberLoginLogService")
class MemberLoginLogServiceImpl : ServiceImpl<MemberLoginLogDao, MemberLoginLogEntity>() , MemberLoginLogService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberLoginLogEntity>().getPage(params),
            QueryWrapper<MemberLoginLogEntity>()
        )
        return PageUtils(page)
    }
}