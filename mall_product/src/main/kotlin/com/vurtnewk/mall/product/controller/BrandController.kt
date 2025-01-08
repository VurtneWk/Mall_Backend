package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.BrandEntity
import com.vurtnewk.mall.product.service.BrandService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import jakarta.validation.Valid

/**
 * 品牌
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/brand")
class BrandController @Autowired constructor(
    private val brandService: BrandService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = brandService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    // @RequiresPermissions("product:brand:info")
    fun info(@PathVariable("brandId") brandId: Long): R {
        val brand: BrandEntity = brandService.getById(brandId)
        return R.ok().put("brand", brand)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    fun save(@RequestBody @Valid  brand: BrandEntity): R {
        brandService.save(brand)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:brand:update")
    fun update(@RequestBody brand: BrandEntity): R {
        brandService.updateById(brand)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:brand:delete")
    fun delete(@RequestBody brandIds: Array<Long>): R {
        brandService.removeByIds(brandIds.asList())
        return R.ok()
    }
}