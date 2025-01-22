package com.vurtnewk.mall.product.dao

import com.vurtnewk.mall.product.entity.SkuSaleAttrValueEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.vurtnewk.mall.product.vo.SkuItemSaleAttrVo
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * sku销售属性&值
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@Mapper
interface SkuSaleAttrValueDao : BaseMapper<SkuSaleAttrValueEntity> {
    fun getSaleAttrsBySpuId(@Param("spuId") spuId: Long): List<SkuItemSaleAttrVo>
    fun getSkuSaleAttrValuesAsStringList(@Param("skuId") skuId: Long): List<String>

}
