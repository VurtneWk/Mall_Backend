package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.SeckillSessionEntity
import com.vurtnewk.mall.coupon.service.SeckillSessionService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 秒杀活动场次
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/seckillsession")
class SeckillSessionController @Autowired constructor(
    private val seckillSessionService: SeckillSessionService,
) {


    @GetMapping("/latest3DaySession")
    fun getLatest3DaySession(): R {
        val list = seckillSessionService.getLatest3DaySession()
        return R.ok().putData(list)
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:seckillsession:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = seckillSessionService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:seckillsession:info")
    fun info(@PathVariable("id") id: Long): R {
        val seckillSession: SeckillSessionEntity = seckillSessionService.getById(id)
        return R.ok().put("seckillSession", seckillSession)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:seckillsession:save")
    fun save(@RequestBody seckillSession: SeckillSessionEntity): R {
        seckillSessionService.save(seckillSession)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:seckillsession:update")
    fun update(@RequestBody seckillSession: SeckillSessionEntity): R {
        seckillSessionService.updateById(seckillSession)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:seckillsession:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        seckillSessionService.removeByIds(ids.asList())
        return R.ok()
    }
}