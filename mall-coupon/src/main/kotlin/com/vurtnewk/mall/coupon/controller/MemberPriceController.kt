package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.MemberPriceEntity
import com.vurtnewk.mall.coupon.service.MemberPriceService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 商品会员价格
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/memberprice")
class MemberPriceController @Autowired constructor(
        private val memberPriceService: MemberPriceService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:memberprice:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberPriceService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:memberprice:info")
    fun info(@PathVariable("id") id: Long): R {
        val memberPrice: MemberPriceEntity = memberPriceService.getById(id)
        return R.ok().put("memberPrice", memberPrice)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:memberprice:save")
    fun save(@RequestBody memberPrice: MemberPriceEntity): R {
            memberPriceService.save(memberPrice)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:memberprice:update")
    fun update(@RequestBody memberPrice: MemberPriceEntity): R {
            memberPriceService.updateById(memberPrice)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:memberprice:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            memberPriceService.removeByIds(ids.asList())
        return R.ok()
    }
}