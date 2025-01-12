package com.vurtnewk.mall.coupon.controller

import com.vurtnewk.common.dto.SkuReductionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.SkuFullReductionEntity
import com.vurtnewk.mall.coupon.service.SkuFullReductionService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 商品满减信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/skufullreduction")
class SkuFullReductionController @Autowired constructor(
    private val skuFullReductionService: SkuFullReductionService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:skufullreduction:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = skuFullReductionService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:skufullreduction:info")
    fun info(@PathVariable("id") id: Long): R {
        val skuFullReduction: SkuFullReductionEntity = skuFullReductionService.getById(id)
        return R.ok().put("skuFullReduction", skuFullReduction)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:skufullreduction:save")
    fun save(@RequestBody skuFullReduction: SkuFullReductionEntity): R {
        skuFullReductionService.save(skuFullReduction)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:skufullreduction:update")
    fun update(@RequestBody skuFullReduction: SkuFullReductionEntity): R {
        skuFullReductionService.updateById(skuFullReduction)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:skufullreduction:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        skuFullReductionService.removeByIds(ids.asList())
        return R.ok()
    }

    @PostMapping("/saveInfo")
    //@RequiresPermissions("coupon:skufullreduction:list")
    fun saveInfo(@RequestParam skuReductionDto: SkuReductionDto): R {
        skuFullReductionService.saveSkuReduction(skuReductionDto)
        return R.ok()
    }

}