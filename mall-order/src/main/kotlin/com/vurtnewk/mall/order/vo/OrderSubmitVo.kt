package com.vurtnewk.mall.order.vo

import java.math.BigDecimal

/**
 * 封装订单提交的数据
 * @author   vurtnewk
 * @since    2025/1/24 20:35
 */
data class OrderSubmitVo(
    var addrId: Long,//收货地址ID
    var payPrice: BigDecimal,//应付价格
    var orderToken: String,//防重令牌
    var payType: Int? = 0, //支付方式
    var note: String? = null,
    // 没有提交订单的商品数据， 提交后再去购物车获取了数据
)