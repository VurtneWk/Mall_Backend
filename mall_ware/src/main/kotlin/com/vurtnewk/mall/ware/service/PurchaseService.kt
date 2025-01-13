package com.vurtnewk.mall.ware.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.ware.entity.PurchaseEntity
import com.vurtnewk.mall.ware.vo.MergePurchaseOrderVo

/**
 * 采购信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
interface PurchaseService : IService<PurchaseEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun queryUnreceivedList(params: Map<String, Any>): PageUtils
    fun mergePurchaseOrder(purchaseOrderVo: MergePurchaseOrderVo)
}

