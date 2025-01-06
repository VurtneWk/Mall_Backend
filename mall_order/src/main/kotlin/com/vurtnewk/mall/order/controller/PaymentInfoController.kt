package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.PaymentInfoEntity
import com.vurtnewk.mall.order.service.PaymentInfoService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 支付信息表
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@RestController
@RequestMapping("order/paymentinfo")
class PaymentInfoController @Autowired constructor(
        private val paymentInfoService: PaymentInfoService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:paymentinfo:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = paymentInfoService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:paymentinfo:info")
    fun info(@PathVariable("id") id: Long): R {
        val paymentInfo: PaymentInfoEntity = paymentInfoService.getById(id)
        return R.ok().put("paymentInfo", paymentInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:paymentinfo:save")
    fun save(@RequestBody paymentInfo: PaymentInfoEntity): R {
            paymentInfoService.save(paymentInfo)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:paymentinfo:update")
    fun update(@RequestBody paymentInfo: PaymentInfoEntity): R {
            paymentInfoService.updateById(paymentInfo)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:paymentinfo:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            paymentInfoService.removeByIds(ids.asList())
        return R.ok()
    }
}