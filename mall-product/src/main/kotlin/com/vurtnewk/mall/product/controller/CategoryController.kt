package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.CategoryEntity
import com.vurtnewk.mall.product.service.CategoryService
import com.vurtnewk.common.utils.R

/**
 * 商品三级分类
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/category")
class CategoryController @Autowired constructor(
    private val categoryService: CategoryService
) {

    /**
     * 查出所有分类以及子分类，已树形结构组装
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("product:category:list")
    fun list(): R {
        return R.ok().put("data", categoryService.listWithTree())
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    // @RequiresPermissions("product:category:info")
    fun info(@PathVariable("catId") catId: Long): R {
        val category: CategoryEntity = categoryService.getById(catId)
        return R.ok().put("data", category)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    fun save(@RequestBody category: CategoryEntity): R {
        categoryService.save(category)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:category:update")
    fun update(@RequestBody category: CategoryEntity): R {
//        categoryService.updateById(category)
        categoryService.updateCascade(category)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:category:delete")
    fun delete(@RequestBody catIds: Array<Long>): R {
        //原来的 为检测
//        categoryService.removeByIds(catIds.asList())

        //1 检查当前删除的菜单 是否被其它地方引用过
        categoryService.removeMenuByIds(catIds.asList())

        return R.ok()
    }
}