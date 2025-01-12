package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.SpuBoundsEntity
import com.vurtnewk.mall.coupon.service.SpuBoundsService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 商品spu积分设置
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/spubounds")
class SpuBoundsController @Autowired constructor(
    private val spuBoundsService: SpuBoundsService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:spubounds:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = spuBoundsService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:spubounds:info")
    fun info(@PathVariable("id") id: Long): R {
        val spuBounds: SpuBoundsEntity = spuBoundsService.getById(id)
        return R.ok().put("spuBounds", spuBounds)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:spubounds:save")
    fun save(@RequestBody spuBounds: SpuBoundsEntity): R {
        spuBoundsService.save(spuBounds)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:spubounds:update")
    fun update(@RequestBody spuBounds: SpuBoundsEntity): R {
        spuBoundsService.updateById(spuBounds)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:spubounds:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        spuBoundsService.removeByIds(ids.asList())
        return R.ok()
    }
}