package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberReceiveAddressDao
import com.vurtnewk.mall.member.entity.MemberReceiveAddressEntity
import com.vurtnewk.mall.member.service.MemberReceiveAddressService


@Service("memberReceiveAddressService")
class MemberReceiveAddressServiceImpl : ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity>(), MemberReceiveAddressService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberReceiveAddressEntity>().getPage(params),
            QueryWrapper<MemberReceiveAddressEntity>()
        )
        return PageUtils(page)
    }

    override fun getAddress(memberId: Long): List<MemberReceiveAddressEntity> {
        return KtQueryChainWrapper(MemberReceiveAddressEntity::class.java)
            .eq(MemberReceiveAddressEntity::memberId, memberId)
            .list()
    }
}