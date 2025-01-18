package com.vurtnewk.mall.search.service

import com.vurtnewk.mall.search.vo.SearchParam
import com.vurtnewk.mall.search.vo.SearchResult

/**
 *
 * @author   vurtnewk
 * @since    2025/1/18 02:41
 */
interface MallSearchService {
    fun search(param: SearchParam): SearchResult?
}