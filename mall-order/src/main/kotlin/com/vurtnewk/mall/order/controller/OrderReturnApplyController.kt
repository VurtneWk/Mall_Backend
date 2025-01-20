package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.OrderReturnApplyEntity
import com.vurtnewk.mall.order.service.OrderReturnApplyService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 订单退货申请
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@RestController
@RequestMapping("order/orderreturnapply")
class OrderReturnApplyController @Autowired constructor(
        private val orderReturnApplyService: OrderReturnApplyService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:orderreturnapply:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = orderReturnApplyService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:orderreturnapply:info")
    fun info(@PathVariable("id") id: Long): R {
        val orderReturnApply: OrderReturnApplyEntity = orderReturnApplyService.getById(id)
        return R.ok().put("orderReturnApply", orderReturnApply)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:orderreturnapply:save")
    fun save(@RequestBody orderReturnApply: OrderReturnApplyEntity): R {
            orderReturnApplyService.save(orderReturnApply)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:orderreturnapply:update")
    fun update(@RequestBody orderReturnApply: OrderReturnApplyEntity): R {
            orderReturnApplyService.updateById(orderReturnApply)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:orderreturnapply:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            orderReturnApplyService.removeByIds(ids.asList())
        return R.ok()
    }
}