package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.HomeSubjectEntity
import com.vurtnewk.mall.coupon.service.HomeSubjectService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/homesubject")
class HomeSubjectController @Autowired constructor(
        private val homeSubjectService: HomeSubjectService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:homesubject:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = homeSubjectService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:homesubject:info")
    fun info(@PathVariable("id") id: Long): R {
        val homeSubject: HomeSubjectEntity = homeSubjectService.getById(id)
        return R.ok().put("homeSubject", homeSubject)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:homesubject:save")
    fun save(@RequestBody homeSubject: HomeSubjectEntity): R {
            homeSubjectService.save(homeSubject)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:homesubject:update")
    fun update(@RequestBody homeSubject: HomeSubjectEntity): R {
            homeSubjectService.updateById(homeSubject)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:homesubject:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            homeSubjectService.removeByIds(ids.asList())
        return R.ok()
    }
}