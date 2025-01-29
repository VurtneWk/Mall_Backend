package com.vurtnewk.mall.ware.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.ware.dao.WareOrderTaskDao
import com.vurtnewk.mall.ware.entity.WareOrderTaskEntity
import com.vurtnewk.mall.ware.service.WareOrderTaskService


@Service("wareOrderTaskService")
class WareOrderTaskServiceImpl : ServiceImpl<WareOrderTaskDao, WareOrderTaskEntity>(), WareOrderTaskService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<WareOrderTaskEntity>().getPage(params),
            QueryWrapper<WareOrderTaskEntity>()
        )
        return PageUtils(page)
    }

    override fun getOrderTaskByOrderSn(orderSn: String): WareOrderTaskEntity? {
        return KtQueryChainWrapper(WareOrderTaskEntity::class.java)
            .eq(WareOrderTaskEntity::orderSn, orderSn)
            .one()
    }
}