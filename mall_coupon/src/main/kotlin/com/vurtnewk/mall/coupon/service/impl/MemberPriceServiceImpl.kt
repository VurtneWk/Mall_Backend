package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.MemberPriceDao
import com.vurtnewk.mall.coupon.entity.MemberPriceEntity
import com.vurtnewk.mall.coupon.service.MemberPriceService


@Service("memberPriceService")
class MemberPriceServiceImpl : ServiceImpl<MemberPriceDao, MemberPriceEntity>() , MemberPriceService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberPriceEntity>().getPage(params),
            QueryWrapper<MemberPriceEntity>()
        )
        return PageUtils(page)
    }
}