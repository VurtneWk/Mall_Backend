package com.vurtnewk.mall.ware.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.ware.entity.PurchaseDetailEntity
import com.vurtnewk.mall.ware.service.PurchaseDetailService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 *
 * 采购需求
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@RestController
@RequestMapping("ware/purchasedetail")
class PurchaseDetailController @Autowired constructor(
    private val purchaseDetailService: PurchaseDetailService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:purchasedetail:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = purchaseDetailService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("ware:purchasedetail:info")
    fun info(@PathVariable("id") id: Long): R {
        val purchaseDetail: PurchaseDetailEntity = purchaseDetailService.getById(id)
        return R.ok().put("purchaseDetail", purchaseDetail)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:purchasedetail:save")
    fun save(@RequestBody purchaseDetail: PurchaseDetailEntity): R {
        purchaseDetailService.save(purchaseDetail)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("ware:purchasedetail:update")
    fun update(@RequestBody purchaseDetail: PurchaseDetailEntity): R {
        purchaseDetailService.updateById(purchaseDetail)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("ware:purchasedetail:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        purchaseDetailService.removeByIds(ids.asList())
        return R.ok()
    }
}