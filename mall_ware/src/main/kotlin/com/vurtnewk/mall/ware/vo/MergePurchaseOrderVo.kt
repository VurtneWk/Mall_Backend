package com.vurtnewk.mall.ware.vo

import kotlinx.serialization.Serializable

/**
 * 合并采购单VO
 * @author   vurtnewk
 * @since    2025/1/13 16:04
 */
@Serializable
class MergePurchaseOrderVo(
     var purchaseId: Long? = null,
     val items: List<Long>? = null
)