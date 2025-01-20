package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.GrowthChangeHistoryEntity
import com.vurtnewk.mall.member.service.GrowthChangeHistoryService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 成长值变化历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/growthchangehistory")
class GrowthChangeHistoryController @Autowired constructor(
        private val growthChangeHistoryService: GrowthChangeHistoryService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:growthchangehistory:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = growthChangeHistoryService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:growthchangehistory:info")
    fun info(@PathVariable("id") id: Long): R {
        val growthChangeHistory: GrowthChangeHistoryEntity = growthChangeHistoryService.getById(id)
        return R.ok().put("growthChangeHistory", growthChangeHistory)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:growthchangehistory:save")
    fun save(@RequestBody growthChangeHistory: GrowthChangeHistoryEntity): R {
            growthChangeHistoryService.save(growthChangeHistory)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:growthchangehistory:update")
    fun update(@RequestBody growthChangeHistory: GrowthChangeHistoryEntity): R {
            growthChangeHistoryService.updateById(growthChangeHistory)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:growthchangehistory:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            growthChangeHistoryService.removeByIds(ids.asList())
        return R.ok()
    }
}