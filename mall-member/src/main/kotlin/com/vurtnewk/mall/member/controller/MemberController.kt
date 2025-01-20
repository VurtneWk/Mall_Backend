package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.MemberEntity
import com.vurtnewk.mall.member.service.MemberService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.member.feign.CouponFeignService

/**
 * 会员
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/member")
class MemberController @Autowired constructor(
    private val memberService: MemberService,
    private val couponFeignService: CouponFeignService
) {

    @RequestMapping("/coupons")
    fun test(): R {
        val entity = MemberEntity()
        entity.nickname = "张三"
        val r = couponFeignService.memberCoupons()
        return R.ok().put("member", entity).put("coupons", r.get("coupons")!!)
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:member:info")
    fun info(@PathVariable("id") id: Long): R {
        val member: MemberEntity = memberService.getById(id)
        return R.ok().put("member", member)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    fun save(@RequestBody member: MemberEntity): R {
        memberService.save(member)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:member:update")
    fun update(@RequestBody member: MemberEntity): R {
        memberService.updateById(member)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:member:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        memberService.removeByIds(ids.asList())
        return R.ok()
    }
}