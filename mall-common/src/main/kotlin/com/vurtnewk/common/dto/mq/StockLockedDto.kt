package com.vurtnewk.common.dto.mq

/**
 * 库存锁定成功的信息
 * @author   vurtnewk
 * @since    2025/1/28 21:43
 */
data class StockLockedDto(
    var id: Long = 0L,
    var stockLockedDetail: StockLockedDetail,
)

data class StockLockedDetail(
    var id: Long? = null,
    /**
     * sku_id
     */
    var skuId: Long? = null,
    /**
     * sku_name
     */
    var skuName: String? = null,
    /**
     * 购买个数
     */
    var skuNum: Int? = null,
    /**
     * 工作单id
     */
    var taskId: Long? = null,
    /**
     * 仓库ID
     */
    var wareId: Long? = null,
    /**
     * 锁定状态
     */
    var lockStatus: Int? = null,
)