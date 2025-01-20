package com.vurtnewk.mall.product.dao

import com.vurtnewk.mall.product.entity.SpuCommentEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 商品评价
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@Mapper
interface SpuCommentDao : BaseMapper<SpuCommentEntity> {
	
}
