package com.vurtnewk.mall.member.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.member.entity.MemberCollectSpuEntity

/**
 * 会员收藏的商品
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
interface MemberCollectSpuService : IService<MemberCollectSpuEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

