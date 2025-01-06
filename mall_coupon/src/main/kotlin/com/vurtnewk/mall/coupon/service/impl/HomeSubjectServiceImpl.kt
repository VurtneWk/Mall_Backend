package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.HomeSubjectDao
import com.vurtnewk.mall.coupon.entity.HomeSubjectEntity
import com.vurtnewk.mall.coupon.service.HomeSubjectService


@Service("homeSubjectService")
class HomeSubjectServiceImpl : ServiceImpl<HomeSubjectDao, HomeSubjectEntity>() , HomeSubjectService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<HomeSubjectEntity>().getPage(params),
            QueryWrapper<HomeSubjectEntity>()
        )
        return PageUtils(page)
    }
}