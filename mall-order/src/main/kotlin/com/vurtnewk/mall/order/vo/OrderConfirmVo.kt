package com.vurtnewk.mall.order.vo

import java.math.BigDecimal

/**
 * 订单确认页需要的数据
 * @author   vurtnewk
 * @since    2025/1/23 20:10
 */
class OrderConfirmVo {

    /**
     * 收货地址 ums_member_receive_address
     */
    var address: List<MemberAddressVo> = emptyList()

    /**
     * 所有选中的购物项
     */
    var items: List<OrderItemVo> = emptyList()

    // 发票...记录

    // 优惠券..
    var integration: Int? = null

    var payPrice: BigDecimal? = null
}


class MemberAddressVo {
    var id: Long? = null

    /**
     * member_id
     */
    var memberId: Long? = null

    /**
     * 收货人姓名
     */
    var name: String? = null

    /**
     * 电话
     */
    var phone: String? = null

    /**
     * 邮政编码
     */
    var postCode: String? = null

    /**
     * 省份/直辖市
     */
    var province: String? = null

    /**
     * 城市
     */
    var city: String? = null

    /**
     * 区
     */
    var region: String? = null

    /**
     * 详细地址(街道)
     */
    var detailAddress: String? = null

    /**
     * 省市区代码
     */
    var areacode: String? = null

    /**
     * 是否默认
     */
    var defaultStatus: Int? = null
}

class OrderItemVo {
    var skuId: Long = 0L
    var title: String? = null
    var image: String? = null
    var skuAttr: List<String> = emptyList()
    var price: BigDecimal = BigDecimal.ZERO
    var count: Int = 0
    val totalPrice: BigDecimal
        get() {
            return this.price.multiply(BigDecimal(this.count))
        }
}