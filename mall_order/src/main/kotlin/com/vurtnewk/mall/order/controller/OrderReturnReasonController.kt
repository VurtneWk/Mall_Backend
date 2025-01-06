package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.OrderReturnReasonEntity
import com.vurtnewk.mall.order.service.OrderReturnReasonService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 退货原因
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@RestController
@RequestMapping("order/orderreturnreason")
class OrderReturnReasonController @Autowired constructor(
        private val orderReturnReasonService: OrderReturnReasonService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:orderreturnreason:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = orderReturnReasonService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:orderreturnreason:info")
    fun info(@PathVariable("id") id: Long): R {
        val orderReturnReason: OrderReturnReasonEntity = orderReturnReasonService.getById(id)
        return R.ok().put("orderReturnReason", orderReturnReason)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:orderreturnreason:save")
    fun save(@RequestBody orderReturnReason: OrderReturnReasonEntity): R {
            orderReturnReasonService.save(orderReturnReason)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:orderreturnreason:update")
    fun update(@RequestBody orderReturnReason: OrderReturnReasonEntity): R {
            orderReturnReasonService.updateById(orderReturnReason)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:orderreturnreason:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            orderReturnReasonService.removeByIds(ids.asList())
        return R.ok()
    }
}