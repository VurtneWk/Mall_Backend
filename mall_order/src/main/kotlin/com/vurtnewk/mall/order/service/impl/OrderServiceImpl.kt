package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.OrderDao
import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.service.OrderService


@Service("orderService")
class OrderServiceImpl : ServiceImpl<OrderDao, OrderEntity>() , OrderService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderEntity>().getPage(params),
            QueryWrapper<OrderEntity>()
        )
        return PageUtils(page)
    }
}