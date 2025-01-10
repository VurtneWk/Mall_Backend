package com.vurtnewk.common.utils.ext

import com.baomidou.mybatisplus.core.metadata.IPage
import com.vurtnewk.common.utils.PageUtils

/**
 * 分页扩展
 * @author   vurtnewk
 * @since    2025/1/8 23:39
 */

fun IPage<*>.pageUtils(): PageUtils {
    return PageUtils(this)
}