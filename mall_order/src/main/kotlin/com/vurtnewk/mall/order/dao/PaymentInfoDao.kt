package com.vurtnewk.mall.order.dao

import com.vurtnewk.mall.order.entity.PaymentInfoEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 支付信息表
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@Mapper
interface PaymentInfoDao : BaseMapper<PaymentInfoEntity> {
	
}
