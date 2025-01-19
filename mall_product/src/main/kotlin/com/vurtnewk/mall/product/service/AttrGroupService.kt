package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.vo.AttrGroupRelationVO
import com.vurtnewk.mall.product.vo.AttrGroupWithAttrsVO
import com.vurtnewk.mall.product.vo.SpuItemAttrGroupVo

/**
 * 属性分组
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface AttrGroupService : IService<AttrGroupEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils

    fun queryPage(params: Map<String, Any>, catelogId: Long): PageUtils
    fun getRelationAttrs(attrGroupId: Long): List<AttrEntity>
    fun deleteRelation(attrGroupRelationVOList: List<AttrGroupRelationVO>)
    fun attrNotRelation(params: Map<String, Any>, attrGroupId: Long): PageUtils
    fun getAttrGroupWithAttrsByCatelogId(catelogId: Long): List<AttrGroupWithAttrsVO>
    fun getAttrGroupWithAttrsBySpuId(spuId: Long, catalogId: Long): List<SpuItemAttrGroupVo>
}

