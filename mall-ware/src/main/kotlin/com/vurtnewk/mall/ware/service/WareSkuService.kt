package com.vurtnewk.mall.ware.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.vurtnewk.mall.ware.vo.SkuHasStockVo

/**
 * 商品库存
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
interface WareSkuService : IService<WareSkuEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun addStock(skuId: Long, wareId: Long, skuNum: Int)
    fun getSkusHasStock(skuIds: List<Long>): List<SkuHasStockVo>
}

