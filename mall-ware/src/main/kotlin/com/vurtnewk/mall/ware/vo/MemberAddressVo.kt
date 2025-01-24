package com.vurtnewk.mall.ware.vo

/**
 *
 * @author   vurtnewk
 * @since    2025/1/24 15:20
 */
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

    override fun toString(): String {
        return "MemberAddressVo(id=$id, memberId=$memberId, name=$name, phone=$phone, postCode=$postCode, province=$province, city=$city, region=$region, detailAddress=$detailAddress, areacode=$areacode, defaultStatus=$defaultStatus)"
    }

}