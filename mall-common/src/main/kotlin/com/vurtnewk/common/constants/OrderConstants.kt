package com.vurtnewk.common.constants

/**
 *
 * @author   vurtnewk
 * @since    2025/1/29 16:25
 */

object OrderStatus {
    /**
     *     CREATE_NEW(0,"待付款"),
     *     PAYED(1,"已付款"),
     *     SENDED(2,"已发货"),
     *     RECIEVED(3,"已完成"),
     *     CANCLED(4,"已取消"),
     *     SERVICING(5,"售后中"),
     *     SERVICED(6,"售后完成");
     */
    //0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
    const val ORDER_STATUS_PENDING_PAYMENT = 0
    const val ORDER_STATUS_PAYED = 1
    const val ORDER_STATUS_SHIPPED = 2
    const val ORDER_STATUS_COMPLETED = 3
    const val ORDER_STATUS_CLOSED = 4
    const val ORDER_STATUS_SERVICING = 5
    const val ORDER_STATUS_SERVICED = 6
}
