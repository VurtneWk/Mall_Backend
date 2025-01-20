package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.OrderSettingDao
import com.vurtnewk.mall.order.entity.OrderSettingEntity
import com.vurtnewk.mall.order.service.OrderSettingService


@Service("orderSettingService")
class OrderSettingServiceImpl : ServiceImpl<OrderSettingDao, OrderSettingEntity>() , OrderSettingService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderSettingEntity>().getPage(params),
            QueryWrapper<OrderSettingEntity>()
        )
        return PageUtils(page)
    }
}