package com.vurtnewk.mall.order.vo

import java.util.*

/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 19:46
 */
data class PayAsyncVo(
    val gmt_create: String? = null,
    val charset: String? = null,
    val gmt_payment: String? = null,
    val notify_time: Date? = null,
    val subject: String? = null,
    val sign: String? = null,
    val buyer_id: String? = null, //支付者的id
    val body: String? = null, //订单的信息
    val invoice_amount: String? = null, //支付金额
    val version: String? = null,
    val notify_id: String? = null,//通知id
    val fund_bill_list: String? = null,
    val notify_type: String? = null,//通知类型； trade_status_sync
    val out_trade_no: String? = null, //订单号
    val total_amount: String? = null,//支付的总额
    val trade_status: String? = null, //交易状态  TRADE_SUCCESS
    val trade_no: String? = null,//流水号
    val auth_app_id: String? = null,//
    val receipt_amount: String? = null,//商家收到的款
    val point_amount: String? = null,//
    val app_id: String? = null,//应用id
    val buyer_pay_amount: String? = null,//最终支付的金额
    val sign_type: String? = null,//签名类型
    val seller_id: String? = null,//商家的id
)


