package com.vurtnewk.mall.cart.service.impl

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import com.vurtnewk.mall.cart.feign.ProductFeignService
import com.vurtnewk.mall.cart.interceptor.CartInterceptor
import com.vurtnewk.mall.cart.service.CartService
import com.vurtnewk.mall.cart.vo.CartItem
import com.vurtnewk.mall.cart.vo.SkuInfoVo
import com.vurtnewk.mall.cart.vo.UserInfoDto
import org.springframework.data.redis.core.BoundHashOperations
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ThreadPoolExecutor

/**
 *
 * @author   vurtnewk
 * @since    2025/1/22 11:56
 */
@Service
class CartServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate,
    private val productFeignService: ProductFeignService,
    private val threadPoolExecutor: ThreadPoolExecutor,
) : CartService {

    companion object {
        const val CART_PREFIX = "mall:cart:"
    }

    /**
     * 添加商品到购物车
     */
    override fun addToCart(skuId: Long, num: Int): CartItem {
        // 下面使用了匿名内部类，在java中这个 cartItem 需要是final的，在kotlin中为什么不需要？
        // 可以反编译查看，kotlin 中 是通过把 cartItem 包装了一层了引用，导致局部变量里和匿名内部类里都是指向同一个引用
        // 通过这个引用 再引用到 cartItem ， 不管怎么修改 cartItem 的指向，外部、内部最终指向的 cartItem 都是同一个。
        var cartItem = CartItem()
        val cartOps = getCartOps()
        val res = cartOps.get(skuId.toString())
        if (res.isNullOrEmpty()) { //购物车无商品
            // 远程查询当前要添加的商品信息
            val infoFuture = CompletableFuture.runAsync({
                val r = productFeignService.getSkuInfo(skuId)
                val skuInfoVo = r.getData("skuInfo", object : TypeReference<SkuInfoVo>() {})
                cartItem.also {
                    it.count = num
                    it.skuId = skuId
                    it.check = true
                    it.image = skuInfoVo.skuDefaultImg
                    it.title = skuInfoVo.skuTitle
                    it.price = skuInfoVo.price!!
                }
            }, threadPoolExecutor)

            val attrFuture = CompletableFuture.runAsync({
                cartItem.skuAttr = productFeignService.getSkuSaleAttrValues(skuId)
            }, threadPoolExecutor)

            CompletableFuture.allOf(infoFuture, attrFuture).get()

            cartOps.put(skuId.toString(), JSON.toJSONString(cartItem))
        } else {
            cartItem = JSON.parseObject(res, CartItem::class.java)
            cartItem.count += num
            cartOps.put(skuId.toString(), JSON.toJSONString(cartItem))
        }
        return cartItem
    }

    override fun getCartItem(skuId: Long): CartItem? {
        val res = getCartOps().get(skuId.toString())
        return if(res.isNullOrEmpty()){
            null
        }else{
            JSON.parseObject(res, CartItem::class.java)
        }

    }

    /**
     * 获取购物车的操作
     */
    private fun getCartOps(): BoundHashOperations<String, String, String> {
        // 获取用户的登录信息
        val userInfoDto = CartInterceptor.threadLocal.get()
        val cartKey = if (userInfoDto.userId != null) {
            CART_PREFIX + userInfoDto.userId
        } else {
            CART_PREFIX + userInfoDto.userKey
        }
        //        stringRedisTemplate.opsForHash<String,String>().get(cartKey,"1")
        return stringRedisTemplate.boundHashOps(cartKey)
    }
}