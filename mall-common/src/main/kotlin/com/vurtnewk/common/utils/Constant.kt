package com.vurtnewk.common.utils

/**
 * createTime:  2025/1/6 12:51
 * author:      vurtnewk
 * description: 常量
 */
object Constant {
    /**
     * 超级管理员ID
     */
    const val SUPER_ADMIN = 1

    /**
     * 当前页码
     */
    const val PAGE = "page"

    /**
     * 每页显示记录数
     */
    const val LIMIT = "limit"

    /**
     * 排序字段
     */
    const val ORDER_FIELD = "sidx"

    /**
     * 排序方式
     */
    const val ORDER = "order"

    /**
     * 升序
     */
    const val ASC = "asc"

    /**
     * 菜单类型
     */
    enum class MenuType(val value: Int) {
        /**
         * 目录
         */
        CATALOG(0),

        /**
         * 菜单
         */
        MENU(1),

        /**
         * 按钮
         */
        BUTTON(2)
    }

    /**
     * 定时任务状态
     */
    enum class ScheduleStatus(val value: Int) {
        /**
         * 正常
         */
        NORMAL(0),

        /**
         * 暂停
         */
        PAUSE(1)
    }

    /**
     * 云服务商
     */
    enum class CloudService(val value: Int, val validatorGroupClass: Class<*>? = null) {
        /**
         * 七牛云
         */
        QINIU(1),

        /**
         * 阿里云
         */
        ALIYUN(2),

        /**
         * 腾讯云
         */
        QCLOUD(3);

        companion object {
            fun getByValue(value: Int): CloudService {
                return values().firstOrNull { it.value == value }
                    ?: throw IllegalArgumentException("非法的枚举值: $value")
            }
        }
    }
}