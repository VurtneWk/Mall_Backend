package com.vurtnewk.mall.ware.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.ware.dao.PurchaseDao
import com.vurtnewk.mall.ware.entity.PurchaseEntity
import com.vurtnewk.mall.ware.service.PurchaseService


@Service("purchaseService")
class PurchaseServiceImpl : ServiceImpl<PurchaseDao, PurchaseEntity>() , PurchaseService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<PurchaseEntity>().getPage(params),
            QueryWrapper<PurchaseEntity>()
        )
        return PageUtils(page)
    }
}