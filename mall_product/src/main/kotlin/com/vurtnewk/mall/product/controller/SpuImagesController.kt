package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.SpuImagesEntity
import com.vurtnewk.mall.product.service.SpuImagesService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * spu图片
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@RestController
@RequestMapping("product/spuimages")
class SpuImagesController @Autowired constructor(
        private val spuImagesService: SpuImagesService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:spuimages:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = spuImagesService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:spuimages:info")
    fun info(@PathVariable("id") id: Long): R {
        val spuImages: SpuImagesEntity = spuImagesService.getById(id)
        return R.ok().put("spuImages", spuImages)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spuimages:save")
    fun save(@RequestBody spuImages: SpuImagesEntity): R {
            spuImagesService.save(spuImages)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:spuimages:update")
    fun update(@RequestBody spuImages: SpuImagesEntity): R {
            spuImagesService.updateById(spuImages)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:spuimages:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            spuImagesService.removeByIds(ids.asList())
        return R.ok()
    }
}