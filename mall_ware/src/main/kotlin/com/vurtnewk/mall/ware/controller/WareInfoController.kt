package com.vurtnewk.mall.ware.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.ware.entity.WareInfoEntity
import com.vurtnewk.mall.ware.service.WareInfoService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 仓库信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@RestController
@RequestMapping("ware/wareinfo")
class WareInfoController @Autowired constructor(
        private val wareInfoService: WareInfoService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:wareinfo:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = wareInfoService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("ware:wareinfo:info")
    fun info(@PathVariable("id") id: Long): R {
        val wareInfo: WareInfoEntity = wareInfoService.getById(id)
        return R.ok().put("wareInfo", wareInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:wareinfo:save")
    fun save(@RequestBody wareInfo: WareInfoEntity): R {
            wareInfoService.save(wareInfo)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("ware:wareinfo:update")
    fun update(@RequestBody wareInfo: WareInfoEntity): R {
            wareInfoService.updateById(wareInfo)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("ware:wareinfo:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            wareInfoService.removeByIds(ids.asList())
        return R.ok()
    }
}