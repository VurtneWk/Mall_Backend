package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.MemberLoginLogEntity
import com.vurtnewk.mall.member.service.MemberLoginLogService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 会员登录记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/memberloginlog")
class MemberLoginLogController @Autowired constructor(
        private val memberLoginLogService: MemberLoginLogService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:memberloginlog:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberLoginLogService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:memberloginlog:info")
    fun info(@PathVariable("id") id: Long): R {
        val memberLoginLog: MemberLoginLogEntity = memberLoginLogService.getById(id)
        return R.ok().put("memberLoginLog", memberLoginLog)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:memberloginlog:save")
    fun save(@RequestBody memberLoginLog: MemberLoginLogEntity): R {
            memberLoginLogService.save(memberLoginLog)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:memberloginlog:update")
    fun update(@RequestBody memberLoginLog: MemberLoginLogEntity): R {
            memberLoginLogService.updateById(memberLoginLog)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:memberloginlog:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            memberLoginLogService.removeByIds(ids.asList())
        return R.ok()
    }
}