package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.MemberCollectSubjectEntity
import com.vurtnewk.mall.member.service.MemberCollectSubjectService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 会员收藏的专题活动
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/membercollectsubject")
class MemberCollectSubjectController @Autowired constructor(
        private val memberCollectSubjectService: MemberCollectSubjectService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:membercollectsubject:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberCollectSubjectService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:membercollectsubject:info")
    fun info(@PathVariable("id") id: Long): R {
        val memberCollectSubject: MemberCollectSubjectEntity = memberCollectSubjectService.getById(id)
        return R.ok().put("memberCollectSubject", memberCollectSubject)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:membercollectsubject:save")
    fun save(@RequestBody memberCollectSubject: MemberCollectSubjectEntity): R {
            memberCollectSubjectService.save(memberCollectSubject)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:membercollectsubject:update")
    fun update(@RequestBody memberCollectSubject: MemberCollectSubjectEntity): R {
            memberCollectSubjectService.updateById(memberCollectSubject)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:membercollectsubject:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            memberCollectSubjectService.removeByIds(ids.asList())
        return R.ok()
    }
}