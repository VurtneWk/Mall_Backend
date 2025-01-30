package com.vurtnewk.mall.coupon.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.coupon.dao.SeckillSkuRelationDao
import com.vurtnewk.mall.coupon.entity.SeckillSkuRelationEntity
import com.vurtnewk.mall.coupon.service.SeckillSkuRelationService
import org.springframework.stereotype.Service


@Service("seckillSkuRelationService")
class SeckillSkuRelationServiceImpl : ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity>(), SeckillSkuRelationService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val promotionSessionId = params["promotionSessionId"] as? String
        return KtQueryChainWrapper(SeckillSkuRelationEntity::class.java)
            .eq(!promotionSessionId.isNullOrBlank(), SeckillSkuRelationEntity::promotionSessionId, promotionSessionId)
            .toPage(params).pageUtils()
    }
}