package com.vurtnewk.mall.ware.vo

import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable

/**
 * 完成订单采购
 * @author   vurtnewk
 * @since    2025/1/13 20:46
 */
@Serializable
data class PurchaseOrderDoneVo(
    @field:NotNull
    val id: Long,
    val items: List<PurchaseOrderItemDoneVo>
)

@Serializable
data class PurchaseOrderItemDoneVo(
    /**
     *
     */
    @field:NotNull
    val itemId: Long,
    @field:NotNull
    val status: Int,
    val reason: String?
)


