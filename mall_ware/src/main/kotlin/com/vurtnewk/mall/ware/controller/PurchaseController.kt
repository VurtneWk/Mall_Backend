package com.vurtnewk.mall.ware.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.ware.entity.PurchaseEntity
import com.vurtnewk.mall.ware.service.PurchaseService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.ware.vo.MergePurchaseOrderVo
import java.util.*

/**
 * 采购信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@RestController
@RequestMapping("ware/purchase")
class PurchaseController @Autowired constructor(
    private val purchaseService: PurchaseService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:purchase:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = purchaseService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("ware:purchase:info")
    fun info(@PathVariable("id") id: Long): R {
        val purchase: PurchaseEntity = purchaseService.getById(id)
        return R.ok().put("purchase", purchase)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:purchase:save")
    fun save(@RequestBody purchase: PurchaseEntity): R {
        purchase.createTime = Date()
        purchase.updateTime = Date()
        purchaseService.save(purchase)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("ware:purchase:update")
    fun update(@RequestBody purchase: PurchaseEntity): R {
        purchaseService.updateById(purchase)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("ware:purchase:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        purchaseService.removeByIds(ids.asList())
        return R.ok()
    }

    /**
     * ## 查询未领取的采购单
     */
    @GetMapping("/unreceive/list")
    fun unreceivedList(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = purchaseService.queryUnreceivedList(params)
        return R.ok().put("page", page)
    }

    /**
     * ## 合并采购单
     *
     */
    @PostMapping("/merge")
    fun mergePurchaseOrder(@RequestBody purchaseOrderVo: MergePurchaseOrderVo): R {
        purchaseService.mergePurchaseOrder(purchaseOrderVo)
        return R.ok()
    }
}