package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.IntegrationChangeHistoryEntity
import com.vurtnewk.mall.member.service.IntegrationChangeHistoryService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 积分变化历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/integrationchangehistory")
class IntegrationChangeHistoryController @Autowired constructor(
        private val integrationChangeHistoryService: IntegrationChangeHistoryService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:integrationchangehistory:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = integrationChangeHistoryService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:integrationchangehistory:info")
    fun info(@PathVariable("id") id: Long): R {
        val integrationChangeHistory: IntegrationChangeHistoryEntity = integrationChangeHistoryService.getById(id)
        return R.ok().put("integrationChangeHistory", integrationChangeHistory)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:integrationchangehistory:save")
    fun save(@RequestBody integrationChangeHistory: IntegrationChangeHistoryEntity): R {
            integrationChangeHistoryService.save(integrationChangeHistory)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:integrationchangehistory:update")
    fun update(@RequestBody integrationChangeHistory: IntegrationChangeHistoryEntity): R {
            integrationChangeHistoryService.updateById(integrationChangeHistory)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:integrationchangehistory:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            integrationChangeHistoryService.removeByIds(ids.asList())
        return R.ok()
    }
}