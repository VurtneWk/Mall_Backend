package com.vurtnewk.common.utils

import com.baomidou.mybatisplus.core.metadata.IPage
import java.io.Serializable
import kotlin.math.ceil

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
data class PageUtils(
    /**
     * 列表数据
     */
    var list: List<Any> = emptyList(),
    /**
     * 总记录数
     */
    var totalCount: Int = 0,
    /**
     * 每页记录数
     */
    var pageSize: Int = 0,
    /**
     * 当前页数
     */
    var currPage: Int = 0,
    /**
     * 总页数
     */
    var totalPage: Int = 0
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    /**
     * 分页
     * @param list 列表数据
     * @param totalCount 总记录数
     * @param pageSize 每页记录数
     * @param currPage 当前页数
     */
    constructor(list: List<Any>, totalCount: Int, pageSize: Int, currPage: Int) : this() {
        this.list = list
        this.totalCount = totalCount
        this.pageSize = pageSize
        this.currPage = currPage
        this.totalPage = ceil(totalCount.toDouble() / pageSize).toInt()
    }

    /**
     * 分页
     * @param page IPage 对象
     */
    constructor(page: IPage<*>) : this() {
        this.list = page.records
        this.totalCount = page.total.toInt()
        this.pageSize = page.size.toInt()
        this.currPage = page.current.toInt()
        this.totalPage = page.pages.toInt()
    }
}