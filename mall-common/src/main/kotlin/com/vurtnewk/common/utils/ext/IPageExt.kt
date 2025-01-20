package com.vurtnewk.common.utils.ext

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

/**
 * 分页扩展
 * @author   vurtnewk
 * @since    2025/1/8 23:39
 */

fun IPage<*>.pageUtils(): PageUtils {
    return PageUtils(this)
}

fun <T : Any> KtQueryChainWrapper<T>.toPage(params: Map<String, Any>): IPage<T> {
    return this.page(Query<T>().getPage(params))
}