package com.vurtnewk.common.constants

/**
 *
 * @author   vurtnewk
 * @since    2025/1/11 16:00
 */

object ProductConstants {

    /**
     * 属性分类
     */
    enum class AttrEnum(val code: Int, val message: String) {
        ATTR_TYPE_SALE(0, "销售属性"), ATTR_TYPE_BASE(1, "基本属性");
    }

    enum class StatusEnum(val code: Int, val message: String) {
        NEW_SPU(0, "新建"), SPU_UP(1, "商品上架"), SPU_DOWN(2, "商品下架");
    }
}

