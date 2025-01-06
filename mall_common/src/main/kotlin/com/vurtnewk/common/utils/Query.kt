package com.vurtnewk.common.utils

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.vurtnewk.common.xss.SQLFilter

/**
 * createTime:  2025/1/6 12:49
 * author:      vurtnewk
 * description:
 */
class Query<T> {

    /**
     * 获取分页对象（无排序字段）
     * @param params 查询参数
     */
    fun getPage(params: Map<String, Any>): IPage<T> {
        return getPage(params, null, false)
    }

    /**
     * 获取分页对象（可设置默认排序字段）
     * @param params 查询参数
     * @param defaultOrderField 默认排序字段
     * @param isAsc 是否升序
     */
    fun getPage(
        params: Map<String, Any>,
        defaultOrderField: String? = null,
        isAsc: Boolean = false
    ): IPage<T> {
        // 分页参数
        val curPage = (params[Constant.PAGE] as? String)?.toLongOrNull() ?: 1L
        val limit = (params[Constant.LIMIT] as? String)?.toLongOrNull() ?: 10L

        // 分页对象
        val page = Page<T>(curPage, limit)

        // 分页参数放回 map（可选，根据需求）
        (params as? MutableMap<String, Any>)?.set(Constant.PAGE, page)

        // 排序字段
        val orderField = SQLFilter.sqlInject(params[Constant.ORDER_FIELD] as? String)
        val order = params[Constant.ORDER] as? String

        // 前端字段排序
        if (!orderField.isNullOrBlank() && !order.isNullOrBlank()) {
            return if (Constant.ASC.equals(order, ignoreCase = true)) {
                page.addOrder(OrderItem.asc(orderField))
            } else {
                page.addOrder(OrderItem.desc(orderField))
            }
        }

        // 如果没有排序字段，则不排序
        if (defaultOrderField.isNullOrBlank()) {
            return page
        }

        // 默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField))
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField))
        }

        return page
    }
}