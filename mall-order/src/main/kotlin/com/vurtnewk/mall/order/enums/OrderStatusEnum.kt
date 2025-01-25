package com.vurtnewk.mall.order.enums

/**
 * 订单状态枚举
 * @author   vurtnewk
 * @since    2025/1/25 03:59
 */
enum class OrderStatusEnum(val code: Int, val msg: String) {
    CREATE_NEW(0, "待付款"),
    PAYED(1, "已付款"),
    DELIVERED(2, "已发货"),
    RECEIVED(3, "已完成"),
    CANCELED(4, "已取消"),
    SERVICING(5, "售后中"),
    SERVICED(6, "售后完成");
}