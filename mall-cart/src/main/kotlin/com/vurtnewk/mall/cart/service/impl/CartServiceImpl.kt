package com.vurtnewk.mall.cart.service.impl

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import com.vurtnewk.mall.cart.feign.ProductFeignService
import com.vurtnewk.mall.cart.interceptor.CartInterceptor
import com.vurtnewk.mall.cart.service.CartService
import com.vurtnewk.mall.cart.vo.Cart
import com.vurtnewk.mall.cart.vo.CartItem
import com.vurtnewk.mall.cart.vo.SkuInfoVo
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

    /**
     * 获取购物车中的某一项
     */
    override fun getCartItem(skuId: Long): CartItem? {
        val res = getCartOps().get(skuId.toString())
        return if (res.isNullOrEmpty()) {
            null
        } else {
            JSON.parseObject(res, CartItem::class.java)
        }
    }

    override fun getCart(): Cart {
        val cart = Cart()
        val userInfoDto = CartInterceptor.threadLocal.get()
        if (userInfoDto.userId != null) {
            // 临时的数据合并
            val cartKeyNoLogin = CART_PREFIX + userInfoDto.userKey
            val items = getCartItems(cartKeyNoLogin)
            items.forEach {
                addToCart(it.skuId, it.count)
            }
            // 清除数据
            clearCart(cartKeyNoLogin)
            val cartKey = CART_PREFIX + userInfoDto.userId
            cart.items = getCartItems(cartKey)
        } else {
            val cartKey = CART_PREFIX + userInfoDto.userKey
            cart.items = getCartItems(cartKey)
        }
        return cart
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

    /**
     * 获取购物车里的购物项
     */
    private fun getCartItems(cartKey: String): List<CartItem> {
        val values = stringRedisTemplate.boundHashOps<String, String>(cartKey).values()
        return values?.map {
            JSON.parseObject(it, CartItem::class.java)
        } ?: emptyList()
    }

    /**
     * 清除指定key值的购物车
     */
    override fun clearCart(cartKey: String) {
        stringRedisTemplate.delete(cartKey)
    }

    /**
     * 勾选购物项
     */
    override fun checkItem(skuId: Long, checked: Int) {
        val cartItem = this.getCartItem(skuId)
        cartItem?.let {
            it.check = checked == 1
            getCartOps().put(skuId.toString(), JSON.toJSONString(it))
        }
    }

    override fun changeItemCount(skuId: Long, num: Int) {
        val cartItem = this.getCartItem(skuId)
        cartItem?.let {
            it.count = num
            getCartOps().put(skuId.toString(), JSON.toJSONString(it))
        }
    }
}