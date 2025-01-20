package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.MemberStatisticsInfoEntity
import com.vurtnewk.mall.member.service.MemberStatisticsInfoService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 会员统计信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/memberstatisticsinfo")
class MemberStatisticsInfoController @Autowired constructor(
        private val memberStatisticsInfoService: MemberStatisticsInfoService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:memberstatisticsinfo:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberStatisticsInfoService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:memberstatisticsinfo:info")
    fun info(@PathVariable("id") id: Long): R {
        val memberStatisticsInfo: MemberStatisticsInfoEntity = memberStatisticsInfoService.getById(id)
        return R.ok().put("memberStatisticsInfo", memberStatisticsInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:memberstatisticsinfo:save")
    fun save(@RequestBody memberStatisticsInfo: MemberStatisticsInfoEntity): R {
            memberStatisticsInfoService.save(memberStatisticsInfo)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:memberstatisticsinfo:update")
    fun update(@RequestBody memberStatisticsInfo: MemberStatisticsInfoEntity): R {
            memberStatisticsInfoService.updateById(memberStatisticsInfo)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:memberstatisticsinfo:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            memberStatisticsInfoService.removeByIds(ids.asList())
        return R.ok()
    }
}