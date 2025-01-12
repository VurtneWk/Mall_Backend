package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.SkuSaleAttrValueEntity

/**
 * sku销售属性&值
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
interface SkuSaleAttrValueService : IService<SkuSaleAttrValueEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

