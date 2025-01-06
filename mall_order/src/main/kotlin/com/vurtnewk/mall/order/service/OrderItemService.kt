package com.vurtnewk.mall.order.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.order.entity.OrderItemEntity

/**
 * 订单项信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:21
 */
interface OrderItemService : IService<OrderItemEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

