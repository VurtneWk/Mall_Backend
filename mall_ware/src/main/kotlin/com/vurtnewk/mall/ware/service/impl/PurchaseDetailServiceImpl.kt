package com.vurtnewk.mall.ware.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.ware.dao.PurchaseDetailDao
import com.vurtnewk.mall.ware.entity.PurchaseDetailEntity
import com.vurtnewk.mall.ware.service.PurchaseDetailService


@Service("purchaseDetailService")
class PurchaseDetailServiceImpl : ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity>() , PurchaseDetailService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<PurchaseDetailEntity>().getPage(params),
            QueryWrapper<PurchaseDetailEntity>()
        )
        return PageUtils(page)
    }
}