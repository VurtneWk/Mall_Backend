package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.SeckillPromotionEntity
import com.vurtnewk.mall.coupon.service.SeckillPromotionService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 秒杀活动
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/seckillpromotion")
class SeckillPromotionController @Autowired constructor(
        private val seckillPromotionService: SeckillPromotionService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:seckillpromotion:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = seckillPromotionService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:seckillpromotion:info")
    fun info(@PathVariable("id") id: Long): R {
        val seckillPromotion: SeckillPromotionEntity = seckillPromotionService.getById(id)
        return R.ok().put("seckillPromotion", seckillPromotion)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:seckillpromotion:save")
    fun save(@RequestBody seckillPromotion: SeckillPromotionEntity): R {
            seckillPromotionService.save(seckillPromotion)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:seckillpromotion:update")
    fun update(@RequestBody seckillPromotion: SeckillPromotionEntity): R {
            seckillPromotionService.updateById(seckillPromotion)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:seckillpromotion:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            seckillPromotionService.removeByIds(ids.asList())
        return R.ok()
    }
}