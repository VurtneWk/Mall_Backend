package com.vurtnewk.mall.order.dao

import com.vurtnewk.mall.order.entity.RefundInfoEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 退款信息
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@Mapper
interface RefundInfoDao : BaseMapper<RefundInfoEntity> {
	
}
