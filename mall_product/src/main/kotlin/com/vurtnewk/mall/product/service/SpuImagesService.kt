package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.SpuImagesEntity

/**
 * spu图片
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
interface SpuImagesService : IService<SpuImagesEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun saveImages(id: Long, images: List<String>)
}

