package com.vurtnewk.mall.order.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.order.entity.OrderOperateHistoryEntity

/**
 * 订单操作历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
interface OrderOperateHistoryService : IService<OrderOperateHistoryEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

