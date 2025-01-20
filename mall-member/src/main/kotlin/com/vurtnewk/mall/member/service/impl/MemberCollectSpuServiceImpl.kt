package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberCollectSpuDao
import com.vurtnewk.mall.member.entity.MemberCollectSpuEntity
import com.vurtnewk.mall.member.service.MemberCollectSpuService


@Service("memberCollectSpuService")
class MemberCollectSpuServiceImpl : ServiceImpl<MemberCollectSpuDao, MemberCollectSpuEntity>() , MemberCollectSpuService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberCollectSpuEntity>().getPage(params),
            QueryWrapper<MemberCollectSpuEntity>()
        )
        return PageUtils(page)
    }
}