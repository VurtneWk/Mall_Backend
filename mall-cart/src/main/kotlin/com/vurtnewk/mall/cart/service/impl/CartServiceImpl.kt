package com.vurtnewk.mall.cart.service.impl

import com.vurtnewk.mall.cart.service.CartService
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 11:56
 */
@Service
class CartServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate,
) : CartService {


}