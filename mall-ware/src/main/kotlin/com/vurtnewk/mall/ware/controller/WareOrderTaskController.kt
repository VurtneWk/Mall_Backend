package com.vurtnewk.mall.ware.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.ware.entity.WareOrderTaskEntity
import com.vurtnewk.mall.ware.service.WareOrderTaskService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 库存工作单
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@RestController
@RequestMapping("ware/wareordertask")
class WareOrderTaskController @Autowired constructor(
        private val wareOrderTaskService: WareOrderTaskService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:wareordertask:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = wareOrderTaskService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("ware:wareordertask:info")
    fun info(@PathVariable("id") id: Long): R {
        val wareOrderTask: WareOrderTaskEntity = wareOrderTaskService.getById(id)
        return R.ok().put("wareOrderTask", wareOrderTask)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:wareordertask:save")
    fun save(@RequestBody wareOrderTask: WareOrderTaskEntity): R {
            wareOrderTaskService.save(wareOrderTask)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("ware:wareordertask:update")
    fun update(@RequestBody wareOrderTask: WareOrderTaskEntity): R {
            wareOrderTaskService.updateById(wareOrderTask)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("ware:wareordertask:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            wareOrderTaskService.removeByIds(ids.asList())
        return R.ok()
    }
}