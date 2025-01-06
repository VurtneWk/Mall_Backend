package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.OrderReturnReasonDao
import com.vurtnewk.mall.order.entity.OrderReturnReasonEntity
import com.vurtnewk.mall.order.service.OrderReturnReasonService


@Service("orderReturnReasonService")
class OrderReturnReasonServiceImpl : ServiceImpl<OrderReturnReasonDao, OrderReturnReasonEntity>() , OrderReturnReasonService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderReturnReasonEntity>().getPage(params),
            QueryWrapper<OrderReturnReasonEntity>()
        )
        return PageUtils(page)
    }
}