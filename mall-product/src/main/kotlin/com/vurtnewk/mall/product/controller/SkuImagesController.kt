package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.SkuImagesEntity
import com.vurtnewk.mall.product.service.SkuImagesService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * sku图片
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/skuimages")
class SkuImagesController @Autowired constructor(
        private val skuImagesService: SkuImagesService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:skuimages:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = skuImagesService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:skuimages:info")
    fun info(@PathVariable("id") id: Long): R {
        val skuImages: SkuImagesEntity = skuImagesService.getById(id)
        return R.ok().put("skuImages", skuImages)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:skuimages:save")
    fun save(@RequestBody skuImages: SkuImagesEntity): R {
            skuImagesService.save(skuImages)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:skuimages:update")
    fun update(@RequestBody skuImages: SkuImagesEntity): R {
            skuImagesService.updateById(skuImages)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:skuimages:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            skuImagesService.removeByIds(ids.asList())
        return R.ok()
    }
}