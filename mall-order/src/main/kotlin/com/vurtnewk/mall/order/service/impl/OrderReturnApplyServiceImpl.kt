package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.OrderReturnApplyDao
import com.vurtnewk.mall.order.entity.OrderReturnApplyEntity
import com.vurtnewk.mall.order.service.OrderReturnApplyService


@Service("orderReturnApplyService")
class OrderReturnApplyServiceImpl : ServiceImpl<OrderReturnApplyDao, OrderReturnApplyEntity>() , OrderReturnApplyService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderReturnApplyEntity>().getPage(params),
            QueryWrapper<OrderReturnApplyEntity>()
        )
        return PageUtils(page)
    }
}