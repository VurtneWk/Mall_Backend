package com.vurtnewk.mall.coupon.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

/**
 * 秒杀活动场次
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@TableName("sms_seckill_session")
data class SeckillSessionEntity(
    /**
     * id
     */
    @TableId
    var id: Long? = null,
    /**
     * 场次名称
     */
    var name: String? = null,
    /**
     * 每日开始时间
     */
    var startTime: LocalDateTime? = null,
    /**
     * 每日结束时间
     */
    var endTime: LocalDateTime? = null,
    /**
     * 启用状态
     */
    var status: Int? = null,
    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null,
    /**
     * 关联的商品信息
     */
    @TableField(exist = false)
    var relationEntities: List<SeckillSkuRelationEntity>? = null,
)