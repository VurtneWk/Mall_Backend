package com.vurtnewk.mall.product.dao

import com.vurtnewk.mall.product.entity.SpuInfoEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * spu信息
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@Mapper
interface SpuInfoDao : BaseMapper<SpuInfoEntity> {
    fun updateSpuStatus(@Param("spuId") spuId: Long, @Param("code") code: Int)
}
