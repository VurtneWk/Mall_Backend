package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.HomeSubjectSpuEntity
import com.vurtnewk.mall.coupon.service.HomeSubjectSpuService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 专题商品
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/homesubjectspu")
class HomeSubjectSpuController @Autowired constructor(
        private val homeSubjectSpuService: HomeSubjectSpuService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:homesubjectspu:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = homeSubjectSpuService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:homesubjectspu:info")
    fun info(@PathVariable("id") id: Long): R {
        val homeSubjectSpu: HomeSubjectSpuEntity = homeSubjectSpuService.getById(id)
        return R.ok().put("homeSubjectSpu", homeSubjectSpu)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:homesubjectspu:save")
    fun save(@RequestBody homeSubjectSpu: HomeSubjectSpuEntity): R {
            homeSubjectSpuService.save(homeSubjectSpu)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:homesubjectspu:update")
    fun update(@RequestBody homeSubjectSpu: HomeSubjectSpuEntity): R {
            homeSubjectSpuService.updateById(homeSubjectSpu)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:homesubjectspu:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            homeSubjectSpuService.removeByIds(ids.asList())
        return R.ok()
    }
}