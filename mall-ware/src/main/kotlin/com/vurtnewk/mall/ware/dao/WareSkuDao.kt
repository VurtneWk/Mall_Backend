package com.vurtnewk.mall.ware.dao

import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 商品库存
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@Mapper
interface WareSkuDao : BaseMapper<WareSkuEntity> {
    fun updateStock(@Param("skuId") skuId: Long, @Param("wareId") wareId: Long, @Param("skuNum") skuNum: Int)
    fun getSkuStock(@Param("skuId") skuId: Long): Long?
    fun listWareIdHasSkuStock(@Param("skuId") skuId: Long): List<Long>
    fun lockSkuStock(@Param("skuId") skuId: Long, @Param("wareId") wareId: Long, @Param("num") num: Int): Long
    fun unLockStock(@Param("skuId") skuId: Long, @Param("wareId") wareId: Long, @Param("skuNum") skuNum: Int)
}
