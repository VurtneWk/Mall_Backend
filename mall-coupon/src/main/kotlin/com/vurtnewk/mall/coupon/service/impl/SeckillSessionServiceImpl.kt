package com.vurtnewk.mall.coupon.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.coupon.dao.SeckillSessionDao
import com.vurtnewk.mall.coupon.entity.SeckillSessionEntity
import com.vurtnewk.mall.coupon.entity.SeckillSkuRelationEntity
import com.vurtnewk.mall.coupon.service.SeckillSessionService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Service("seckillSessionService")
class SeckillSessionServiceImpl : ServiceImpl<SeckillSessionDao, SeckillSessionEntity>(), SeckillSessionService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        return KtQueryChainWrapper(SeckillSessionEntity::class.java)
            .toPage(params)
            .pageUtils()
    }

    override fun getLatest3DaySession(): List<SeckillSessionEntity> {
        // 计算最近三天 今天的
        // 比如 2022:01:01 00:00:00 到 2022:01:03 23:59:59
        val startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        val endTime = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.MAX)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        return KtQueryChainWrapper(SeckillSessionEntity::class.java)
            .between(SeckillSessionEntity::startTime, startTime, endTime)
            .list()
            .map { seckillSessionEntity ->
                seckillSessionEntity.relationEntities = KtQueryChainWrapper(SeckillSkuRelationEntity::class.java)
                    .eq(SeckillSkuRelationEntity::promotionSessionId, seckillSessionEntity.id)
                    .list()
                seckillSessionEntity
            }
    }
}