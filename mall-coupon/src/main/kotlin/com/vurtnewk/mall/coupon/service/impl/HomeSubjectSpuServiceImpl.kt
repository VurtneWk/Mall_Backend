package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.HomeSubjectSpuDao
import com.vurtnewk.mall.coupon.entity.HomeSubjectSpuEntity
import com.vurtnewk.mall.coupon.service.HomeSubjectSpuService


@Service("homeSubjectSpuService")
class HomeSubjectSpuServiceImpl : ServiceImpl<HomeSubjectSpuDao, HomeSubjectSpuEntity>() , HomeSubjectSpuService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<HomeSubjectSpuEntity>().getPage(params),
            QueryWrapper<HomeSubjectSpuEntity>()
        )
        return PageUtils(page)
    }
}