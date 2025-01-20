package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.OrderItemDao
import com.vurtnewk.mall.order.entity.OrderItemEntity
import com.vurtnewk.mall.order.service.OrderItemService


@Service("orderItemService")
class OrderItemServiceImpl : ServiceImpl<OrderItemDao, OrderItemEntity>() , OrderItemService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderItemEntity>().getPage(params),
            QueryWrapper<OrderItemEntity>()
        )
        return PageUtils(page)
    }
}