package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.HomeAdvDao
import com.vurtnewk.mall.coupon.entity.HomeAdvEntity
import com.vurtnewk.mall.coupon.service.HomeAdvService


@Service("homeAdvService")
class HomeAdvServiceImpl : ServiceImpl<HomeAdvDao, HomeAdvEntity>() , HomeAdvService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<HomeAdvEntity>().getPage(params),
            QueryWrapper<HomeAdvEntity>()
        )
        return PageUtils(page)
    }
}