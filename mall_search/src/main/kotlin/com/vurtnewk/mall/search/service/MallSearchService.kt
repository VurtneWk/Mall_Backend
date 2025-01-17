package com.vurtnewk.mall.search.service

import com.vurtnewk.mall.search.vo.SearchParam

/**
 *
 * @author   vurtnewk
 * @since    2025/1/18 02:41
 */
interface MallSearchService {
    fun search(param: SearchParam)
}