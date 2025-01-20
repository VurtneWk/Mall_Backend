package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberDao
import com.vurtnewk.mall.member.entity.MemberEntity
import com.vurtnewk.mall.member.service.MemberService


@Service("memberService")
class MemberServiceImpl : ServiceImpl<MemberDao, MemberEntity>() , MemberService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberEntity>().getPage(params),
            QueryWrapper<MemberEntity>()
        )
        return PageUtils(page)
    }
}