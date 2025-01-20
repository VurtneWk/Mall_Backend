package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.SkuInfoEntity
import com.vurtnewk.mall.product.vo.SkuItemVo

/**
 * sku信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface SkuInfoService : IService<SkuInfoEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun queryPageByCondition(params: Map<String, Any>): PageUtils
    fun getSkusBySpuId(spuId: Long): List<SkuInfoEntity>
    fun queryItem(skuId: Long): SkuItemVo?
}

