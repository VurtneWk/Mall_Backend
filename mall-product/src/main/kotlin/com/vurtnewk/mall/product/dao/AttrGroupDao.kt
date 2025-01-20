package com.vurtnewk.mall.product.dao

import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.vurtnewk.mall.product.vo.SpuItemAttrGroupVo
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 属性分组
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@Mapper
interface AttrGroupDao : BaseMapper<AttrGroupEntity> {
    fun getAttrGroupWithAttrsBySpuId(@Param("spuId") spuId: Long, @Param("catalogId") catalogId: Long):List<SpuItemAttrGroupVo>

}
