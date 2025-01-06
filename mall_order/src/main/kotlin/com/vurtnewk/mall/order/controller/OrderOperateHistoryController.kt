package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.OrderOperateHistoryEntity
import com.vurtnewk.mall.order.service.OrderOperateHistoryService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 订单操作历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@RestController
@RequestMapping("order/orderoperatehistory")
class OrderOperateHistoryController @Autowired constructor(
        private val orderOperateHistoryService: OrderOperateHistoryService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:orderoperatehistory:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = orderOperateHistoryService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:orderoperatehistory:info")
    fun info(@PathVariable("id") id: Long): R {
        val orderOperateHistory: OrderOperateHistoryEntity = orderOperateHistoryService.getById(id)
        return R.ok().put("orderOperateHistory", orderOperateHistory)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:orderoperatehistory:save")
    fun save(@RequestBody orderOperateHistory: OrderOperateHistoryEntity): R {
            orderOperateHistoryService.save(orderOperateHistory)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:orderoperatehistory:update")
    fun update(@RequestBody orderOperateHistory: OrderOperateHistoryEntity): R {
            orderOperateHistoryService.updateById(orderOperateHistory)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:orderoperatehistory:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            orderOperateHistoryService.removeByIds(ids.asList())
        return R.ok()
    }
}