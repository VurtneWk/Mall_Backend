package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.SkuSaleAttrValueEntity
import com.vurtnewk.mall.product.service.SkuSaleAttrValueService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * sku销售属性&值
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@RestController
@RequestMapping("product/skusaleattrvalue")
class SkuSaleAttrValueController @Autowired constructor(
    private val skuSaleAttrValueService: SkuSaleAttrValueService,
) {


    @GetMapping("/stringList/{skuId}")
    fun getSkuSaleAttrValues(@PathVariable("skuId") skuId: Long): List<String> {
        return skuSaleAttrValueService.getSkuSaleAttrValuesAsStringList(skuId)
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:skusaleattrvalue:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = skuSaleAttrValueService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:skusaleattrvalue:info")
    fun info(@PathVariable("id") id: Long): R {
        val skuSaleAttrValue: SkuSaleAttrValueEntity = skuSaleAttrValueService.getById(id)
        return R.ok().put("skuSaleAttrValue", skuSaleAttrValue)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:skusaleattrvalue:save")
    fun save(@RequestBody skuSaleAttrValue: SkuSaleAttrValueEntity): R {
        skuSaleAttrValueService.save(skuSaleAttrValue)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:skusaleattrvalue:update")
    fun update(@RequestBody skuSaleAttrValue: SkuSaleAttrValueEntity): R {
        skuSaleAttrValueService.updateById(skuSaleAttrValue)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:skusaleattrvalue:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        skuSaleAttrValueService.removeByIds(ids.asList())
        return R.ok()
    }
}