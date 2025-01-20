package com.vurtnewk.mall.order.dao

import com.vurtnewk.mall.order.entity.OrderSettingEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 订单配置信息
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@Mapper
interface OrderSettingDao : BaseMapper<OrderSettingEntity> {
	
}
