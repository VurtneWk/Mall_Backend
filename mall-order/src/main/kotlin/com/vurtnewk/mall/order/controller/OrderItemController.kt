package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.OrderItemEntity
import com.vurtnewk.mall.order.service.OrderItemService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 订单项信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:21
 */
@RestController
@RequestMapping("order/orderitem")
class OrderItemController @Autowired constructor(
        private val orderItemService: OrderItemService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:orderitem:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = orderItemService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:orderitem:info")
    fun info(@PathVariable("id") id: Long): R {
        val orderItem: OrderItemEntity = orderItemService.getById(id)
        return R.ok().put("orderItem", orderItem)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:orderitem:save")
    fun save(@RequestBody orderItem: OrderItemEntity): R {
            orderItemService.save(orderItem)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:orderitem:update")
    fun update(@RequestBody orderItem: OrderItemEntity): R {
            orderItemService.updateById(orderItem)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:orderitem:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            orderItemService.removeByIds(ids.asList())
        return R.ok()
    }
}