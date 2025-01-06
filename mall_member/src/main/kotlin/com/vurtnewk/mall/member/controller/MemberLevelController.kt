package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.MemberLevelEntity
import com.vurtnewk.mall.member.service.MemberLevelService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 会员等级
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/memberlevel")
class MemberLevelController @Autowired constructor(
        private val memberLevelService: MemberLevelService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:memberlevel:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberLevelService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:memberlevel:info")
    fun info(@PathVariable("id") id: Long): R {
        val memberLevel: MemberLevelEntity = memberLevelService.getById(id)
        return R.ok().put("memberLevel", memberLevel)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:memberlevel:save")
    fun save(@RequestBody memberLevel: MemberLevelEntity): R {
            memberLevelService.save(memberLevel)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:memberlevel:update")
    fun update(@RequestBody memberLevel: MemberLevelEntity): R {
            memberLevelService.updateById(memberLevel)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:memberlevel:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            memberLevelService.removeByIds(ids.asList())
        return R.ok()
    }
}