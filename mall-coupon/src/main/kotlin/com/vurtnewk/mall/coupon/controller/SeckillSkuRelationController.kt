package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.SeckillSkuRelationEntity
import com.vurtnewk.mall.coupon.service.SeckillSkuRelationService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 秒杀活动商品关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/seckillskurelation")
class SeckillSkuRelationController @Autowired constructor(
        private val seckillSkuRelationService: SeckillSkuRelationService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:seckillskurelation:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = seckillSkuRelationService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:seckillskurelation:info")
    fun info(@PathVariable("id") id: Long): R {
        val seckillSkuRelation: SeckillSkuRelationEntity = seckillSkuRelationService.getById(id)
        return R.ok().put("seckillSkuRelation", seckillSkuRelation)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:seckillskurelation:save")
    fun save(@RequestBody seckillSkuRelation: SeckillSkuRelationEntity): R {
            seckillSkuRelationService.save(seckillSkuRelation)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:seckillskurelation:update")
    fun update(@RequestBody seckillSkuRelation: SeckillSkuRelationEntity): R {
            seckillSkuRelationService.updateById(seckillSkuRelation)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:seckillskurelation:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            seckillSkuRelationService.removeByIds(ids.asList())
        return R.ok()
    }
}