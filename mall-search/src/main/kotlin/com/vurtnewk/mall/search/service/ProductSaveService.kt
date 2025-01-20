package com.vurtnewk.mall.search.service

import com.vurtnewk.common.dto.SkuEsModelDto

/**
 * 商品上架服务
 * @author   vurtnewk
 * @since    2025/1/15 21:34
 */

interface ProductSaveService {
    fun productStatusUp(skuEsModels: List<SkuEsModelDto>):Boolean
}