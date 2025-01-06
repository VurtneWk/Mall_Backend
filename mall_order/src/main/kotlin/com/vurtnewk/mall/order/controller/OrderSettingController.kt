package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.OrderSettingEntity
import com.vurtnewk.mall.order.service.OrderSettingService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 订单配置信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@RestController
@RequestMapping("order/ordersetting")
class OrderSettingController @Autowired constructor(
        private val orderSettingService: OrderSettingService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:ordersetting:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = orderSettingService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:ordersetting:info")
    fun info(@PathVariable("id") id: Long): R {
        val orderSetting: OrderSettingEntity = orderSettingService.getById(id)
        return R.ok().put("orderSetting", orderSetting)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:ordersetting:save")
    fun save(@RequestBody orderSetting: OrderSettingEntity): R {
            orderSettingService.save(orderSetting)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:ordersetting:update")
    fun update(@RequestBody orderSetting: OrderSettingEntity): R {
            orderSettingService.updateById(orderSetting)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:ordersetting:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            orderSettingService.removeByIds(ids.asList())
        return R.ok()
    }
}