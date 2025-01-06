package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.RefundInfoDao
import com.vurtnewk.mall.order.entity.RefundInfoEntity
import com.vurtnewk.mall.order.service.RefundInfoService


@Service("refundInfoService")
class RefundInfoServiceImpl : ServiceImpl<RefundInfoDao, RefundInfoEntity>() , RefundInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<RefundInfoEntity>().getPage(params),
            QueryWrapper<RefundInfoEntity>()
        )
        return PageUtils(page)
    }
}