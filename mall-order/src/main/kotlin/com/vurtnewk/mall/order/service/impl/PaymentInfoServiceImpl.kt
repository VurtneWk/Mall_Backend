package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.PaymentInfoDao
import com.vurtnewk.mall.order.entity.PaymentInfoEntity
import com.vurtnewk.mall.order.service.PaymentInfoService


@Service("paymentInfoService")
class PaymentInfoServiceImpl : ServiceImpl<PaymentInfoDao, PaymentInfoEntity>() , PaymentInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<PaymentInfoEntity>().getPage(params),
            QueryWrapper<PaymentInfoEntity>()
        )
        return PageUtils(page)
    }
}