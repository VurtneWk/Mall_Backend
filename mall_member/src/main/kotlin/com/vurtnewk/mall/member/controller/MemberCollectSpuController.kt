package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.MemberCollectSpuEntity
import com.vurtnewk.mall.member.service.MemberCollectSpuService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 会员收藏的商品
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/membercollectspu")
class MemberCollectSpuController @Autowired constructor(
        private val memberCollectSpuService: MemberCollectSpuService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:membercollectspu:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberCollectSpuService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:membercollectspu:info")
    fun info(@PathVariable("id") id: Long): R {
        val memberCollectSpu: MemberCollectSpuEntity = memberCollectSpuService.getById(id)
        return R.ok().put("memberCollectSpu", memberCollectSpu)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:membercollectspu:save")
    fun save(@RequestBody memberCollectSpu: MemberCollectSpuEntity): R {
            memberCollectSpuService.save(memberCollectSpu)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:membercollectspu:update")
    fun update(@RequestBody memberCollectSpu: MemberCollectSpuEntity): R {
            memberCollectSpuService.updateById(memberCollectSpu)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:membercollectspu:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            memberCollectSpuService.removeByIds(ids.asList())
        return R.ok()
    }
}