package com.vurtnewk.mall.ware.dao

import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 商品库存
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@Mapper
interface WareSkuDao : BaseMapper<WareSkuEntity> {
	
}
