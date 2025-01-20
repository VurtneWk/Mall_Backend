package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.CommentReplayEntity

/**
 * 商品评价回复关系
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface CommentReplayService : IService<CommentReplayEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

