package com.vurtnewk.common.constants

/**
 * 库存常量
 * @author   vurtnewk
 * @since    2025/1/13 15:35
 */

object WareConstants {

    enum class PurchaseStatusEnum(val code: Int, val msg: String) {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVE(2, "已领取"),
        FINISH(3, "已完成"),
        ERROR(4, "有异常")
    }

    enum class PurchaseDetailStatusEnum(val code: Int, val msg: String) {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        BUYING(2, "正在采购"),
        FINISH(3, "已完成"),
        ERROR(4, "采购失败")
    }

}