package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.ProductAttrValueEntity

/**
 * spu属性值
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface ProductAttrValueService : IService<ProductAttrValueEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun baseAttrList(spuId: Long): List<ProductAttrValueEntity>
    fun updateSpuAttr(spuId: Long, entities: List<ProductAttrValueEntity>)
}

