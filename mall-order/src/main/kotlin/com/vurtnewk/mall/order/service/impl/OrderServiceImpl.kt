package com.vurtnewk.mall.order.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.mall.order.dao.OrderDao
import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.service.OrderService
import com.vurtnewk.mall.order.vo.OrderConfirmVo
import org.springframework.stereotype.Service


@Service("orderService")
class OrderServiceImpl : ServiceImpl<OrderDao, OrderEntity>(), OrderService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    override fun confirmOrder(): OrderConfirmVo {
        val orderConfirmVo = OrderConfirmVo()


        return orderConfirmVo
    }
}