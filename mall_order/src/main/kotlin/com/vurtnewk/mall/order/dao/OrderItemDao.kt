package com.vurtnewk.mall.order.dao

import com.vurtnewk.mall.order.entity.OrderItemEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 订单项信息
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:21
 */
@Mapper
interface OrderItemDao : BaseMapper<OrderItemEntity> {
	
}
