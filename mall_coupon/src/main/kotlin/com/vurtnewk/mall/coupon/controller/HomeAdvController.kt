package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.HomeAdvEntity
import com.vurtnewk.mall.coupon.service.HomeAdvService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 首页轮播广告
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/homeadv")
class HomeAdvController @Autowired constructor(
        private val homeAdvService: HomeAdvService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:homeadv:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = homeAdvService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:homeadv:info")
    fun info(@PathVariable("id") id: Long): R {
        val homeAdv: HomeAdvEntity = homeAdvService.getById(id)
        return R.ok().put("homeAdv", homeAdv)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:homeadv:save")
    fun save(@RequestBody homeAdv: HomeAdvEntity): R {
            homeAdvService.save(homeAdv)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:homeadv:update")
    fun update(@RequestBody homeAdv: HomeAdvEntity): R {
            homeAdvService.updateById(homeAdv)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:homeadv:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            homeAdvService.removeByIds(ids.asList())
        return R.ok()
    }
}