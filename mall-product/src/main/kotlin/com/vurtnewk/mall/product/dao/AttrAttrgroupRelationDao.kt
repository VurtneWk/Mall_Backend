package com.vurtnewk.mall.product.dao

import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.vurtnewk.mall.product.vo.AttrGroupRelationVO
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 属性&属性分组关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@Mapper
interface AttrAttrgroupRelationDao : BaseMapper<AttrAttrgroupRelationEntity> {
    fun deleteRelation(@Param("entities") entities: List<AttrAttrgroupRelationEntity>)
}
