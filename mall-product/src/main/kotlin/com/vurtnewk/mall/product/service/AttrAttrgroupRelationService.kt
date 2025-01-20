package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.vurtnewk.mall.product.vo.AttrGroupRelationVO

/**
 * 属性&属性分组关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface AttrAttrgroupRelationService : IService<AttrAttrgroupRelationEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun saveBatch(attrGroupRelationVOList: List<AttrGroupRelationVO>)
}

