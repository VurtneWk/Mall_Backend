package com.vurtnewk.mall.ware.dao

import com.vurtnewk.mall.ware.entity.WareOrderTaskEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 库存工作单
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@Mapper
interface WareOrderTaskDao : BaseMapper<WareOrderTaskEntity> {
	
}
