package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.CategoryBrandRelationEntity
import com.vurtnewk.mall.product.service.CategoryBrandRelationService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 品牌分类关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/categorybrandrelation")
class CategoryBrandRelationController @Autowired constructor(
        private val categoryBrandRelationService: CategoryBrandRelationService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = categoryBrandRelationService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:categorybrandrelation:info")
    fun info(@PathVariable("id") id: Long): R {
        val categoryBrandRelation: CategoryBrandRelationEntity = categoryBrandRelationService.getById(id)
        return R.ok().put("categoryBrandRelation", categoryBrandRelation)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    fun save(@RequestBody categoryBrandRelation: CategoryBrandRelationEntity): R {
            categoryBrandRelationService.save(categoryBrandRelation)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:categorybrandrelation:update")
    fun update(@RequestBody categoryBrandRelation: CategoryBrandRelationEntity): R {
            categoryBrandRelationService.updateById(categoryBrandRelation)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:categorybrandrelation:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            categoryBrandRelationService.removeByIds(ids.asList())
        return R.ok()
    }
}