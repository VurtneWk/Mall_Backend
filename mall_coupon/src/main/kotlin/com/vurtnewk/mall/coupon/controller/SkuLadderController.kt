package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.SkuLadderEntity
import com.vurtnewk.mall.coupon.service.SkuLadderService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 商品阶梯价格
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/skuladder")
class SkuLadderController @Autowired constructor(
        private val skuLadderService: SkuLadderService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:skuladder:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = skuLadderService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:skuladder:info")
    fun info(@PathVariable("id") id: Long): R {
        val skuLadder: SkuLadderEntity = skuLadderService.getById(id)
        return R.ok().put("skuLadder", skuLadder)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:skuladder:save")
    fun save(@RequestBody skuLadder: SkuLadderEntity): R {
            skuLadderService.save(skuLadder)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:skuladder:update")
    fun update(@RequestBody skuLadder: SkuLadderEntity): R {
            skuLadderService.updateById(skuLadder)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:skuladder:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            skuLadderService.removeByIds(ids.asList())
        return R.ok()
    }
}