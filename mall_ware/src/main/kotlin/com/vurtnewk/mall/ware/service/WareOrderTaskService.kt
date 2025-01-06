package com.vurtnewk.mall.ware.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.ware.entity.WareOrderTaskEntity

/**
 * 库存工作单
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
interface WareOrderTaskService : IService<WareOrderTaskEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
}

