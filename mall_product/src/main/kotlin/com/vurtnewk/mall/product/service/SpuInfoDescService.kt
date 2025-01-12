package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.SpuInfoDescEntity
import com.vurtnewk.mall.product.vo.SpuInfoVO

/**
 * spu信息介绍
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
interface SpuInfoDescService : IService<SpuInfoDescEntity> {

    fun queryPage(params: Map<String, Any>): PageUtils
    fun saveSpuInfoDesc(spuInfoDescEntity: SpuInfoDescEntity)
}

