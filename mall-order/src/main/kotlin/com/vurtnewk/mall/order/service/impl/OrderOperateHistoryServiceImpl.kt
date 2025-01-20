package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.OrderOperateHistoryDao
import com.vurtnewk.mall.order.entity.OrderOperateHistoryEntity
import com.vurtnewk.mall.order.service.OrderOperateHistoryService


@Service("orderOperateHistoryService")
class OrderOperateHistoryServiceImpl : ServiceImpl<OrderOperateHistoryDao, OrderOperateHistoryEntity>() , OrderOperateHistoryService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderOperateHistoryEntity>().getPage(params),
            QueryWrapper<OrderOperateHistoryEntity>()
        )
        return PageUtils(page)
    }
}