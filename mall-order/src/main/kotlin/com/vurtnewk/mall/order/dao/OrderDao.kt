package com.vurtnewk.mall.order.dao

import com.vurtnewk.mall.order.entity.OrderEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 订单
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:21
 */
@Mapper
interface OrderDao : BaseMapper<OrderEntity> {
    fun updateOrderStatus(@Param("outTradeNo") outTradeNo: String, @Param("orderStatus") orderStatus: Int, @Param("payType") payType: Int)

}
