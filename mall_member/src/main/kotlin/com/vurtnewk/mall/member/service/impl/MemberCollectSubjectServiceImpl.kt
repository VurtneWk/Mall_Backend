package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberCollectSubjectDao
import com.vurtnewk.mall.member.entity.MemberCollectSubjectEntity
import com.vurtnewk.mall.member.service.MemberCollectSubjectService


@Service("memberCollectSubjectService")
class MemberCollectSubjectServiceImpl : ServiceImpl<MemberCollectSubjectDao, MemberCollectSubjectEntity>() , MemberCollectSubjectService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberCollectSubjectEntity>().getPage(params),
            QueryWrapper<MemberCollectSubjectEntity>()
        )
        return PageUtils(page)
    }
}