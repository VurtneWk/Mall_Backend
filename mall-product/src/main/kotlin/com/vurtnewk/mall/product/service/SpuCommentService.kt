package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.SpuCommentEntity

/**
 * 商品评价
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
interface SpuCommentService : IService<SpuCommentEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

