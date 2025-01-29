package com.vurtnewk.common.constants

/**
 *
 * @author   vurtnewk
 * @since    2025/1/29 16:25
 */

object OrderStatus {
    //0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
    const val ORDER_STATUS_PENDING_PAYMENT = 0
    const val ORDER_STATUS_PENDING_SHIPMENT = 1
    const val ORDER_STATUS_SHIPPED = 2
    const val ORDER_STATUS_COMPLETED = 3
    const val ORDER_STATUS_CLOSED = 4
    const val ORDER_STATUS_INVALID = 5
}
