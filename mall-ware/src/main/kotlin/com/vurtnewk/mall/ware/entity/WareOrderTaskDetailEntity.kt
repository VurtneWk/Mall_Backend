package com.vurtnewk.mall.ware.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 * 库存工作单
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@TableName("wms_ware_order_task_detail")
data class WareOrderTaskDetailEntity(
    /**
     * id
     */
    @TableId
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
     *
     */
    var lockStatus: Int? = null,
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}