package com.vurtnewk.mall.order.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.order.entity.RefundInfoEntity
import com.vurtnewk.mall.order.service.RefundInfoService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 退款信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:42:22
 */
@RestController
@RequestMapping("order/refundinfo")
class RefundInfoController @Autowired constructor(
        private val refundInfoService: RefundInfoService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:refundinfo:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = refundInfoService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("order:refundinfo:info")
    fun info(@PathVariable("id") id: Long): R {
        val refundInfo: RefundInfoEntity = refundInfoService.getById(id)
        return R.ok().put("refundInfo", refundInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:refundinfo:save")
    fun save(@RequestBody refundInfo: RefundInfoEntity): R {
            refundInfoService.save(refundInfo)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("order:refundinfo:update")
    fun update(@RequestBody refundInfo: RefundInfoEntity): R {
            refundInfoService.updateById(refundInfo)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("order:refundinfo:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            refundInfoService.removeByIds(ids.asList())
        return R.ok()
    }
}