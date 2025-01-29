package com.vurtnewk.mall.order.vo

/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 00:29
 */
data class PayVo(
    /**
     * 商户订单号 必填
     */
    var outTradeNo: String = "",
    /**
     * 订单名称 必填
     */
    var subject: String = "",
    /**
     * 付款金额 必填
     */
    var totalAmount: String = "",
    /**
     * 商品描述 可空
     */
    var body: String = "",
)
