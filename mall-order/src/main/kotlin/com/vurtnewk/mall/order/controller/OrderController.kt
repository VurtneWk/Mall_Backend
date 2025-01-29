package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.OrderEntity
import com.vurtnewk.mall.order.service.OrderService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 订单
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:21
 */
@RestController
@RequestMapping("order/order")
class OrderController @Autowired constructor(
        private val orderService: OrderService
) {

    /**
     * ## 查询订单状态
     */
    @GetMapping("/status/{orderSn}")
    fun getOrderStatus(@PathVariable("orderSn") orderSn: String): R {
        val orderEntity = orderService.getOrderByOrderSn(orderSn)
        return R.ok().putData(orderEntity)
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:order:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = orderService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:order:info")
    fun info(@PathVariable("id") id: Long): R {
        val order: OrderEntity = orderService.getById(id)
        return R.ok().put("order", order)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:order:save")
    fun save(@RequestBody order: OrderEntity): R {
            orderService.save(order)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:order:update")
    fun update(@RequestBody order: OrderEntity): R {
            orderService.updateById(order)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:order:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            orderService.removeByIds(ids.asList())
        return R.ok()
    }
}