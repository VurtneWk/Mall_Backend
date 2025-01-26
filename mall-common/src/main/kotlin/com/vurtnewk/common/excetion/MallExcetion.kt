package com.vurtnewk.common.excetion

/**
 * 异常类
 * @author   vurtnewk
 * @since    2025/1/25 08:56
 */

class NoStockException(skuId: Long?) : RuntimeException("${skuId ?: ""}没有足够库存了")