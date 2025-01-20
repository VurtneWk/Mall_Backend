package com.vurtnewk.mall.ware.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.ware.entity.WareOrderTaskDetailEntity
import com.vurtnewk.mall.ware.service.WareOrderTaskDetailService
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
@RequestMapping("ware/wareordertaskdetail")
class WareOrderTaskDetailController @Autowired constructor(
        private val wareOrderTaskDetailService: WareOrderTaskDetailService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:wareordertaskdetail:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = wareOrderTaskDetailService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("ware:wareordertaskdetail:info")
    fun info(@PathVariable("id") id: Long): R {
        val wareOrderTaskDetail: WareOrderTaskDetailEntity = wareOrderTaskDetailService.getById(id)
        return R.ok().put("wareOrderTaskDetail", wareOrderTaskDetail)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:wareordertaskdetail:save")
    fun save(@RequestBody wareOrderTaskDetail: WareOrderTaskDetailEntity): R {
            wareOrderTaskDetailService.save(wareOrderTaskDetail)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("ware:wareordertaskdetail:update")
    fun update(@RequestBody wareOrderTaskDetail: WareOrderTaskDetailEntity): R {
            wareOrderTaskDetailService.updateById(wareOrderTaskDetail)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("ware:wareordertaskdetail:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            wareOrderTaskDetailService.removeByIds(ids.asList())
        return R.ok()
    }
}