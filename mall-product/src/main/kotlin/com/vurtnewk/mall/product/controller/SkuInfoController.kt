package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.SkuInfoEntity
import com.vurtnewk.mall.product.service.SkuInfoService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import java.math.BigDecimal

/**
 * sku信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/skuinfo")
class SkuInfoController @Autowired constructor(
    private val skuInfoService: SkuInfoService,
) {


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:skuinfo:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = skuInfoService.queryPageByCondition(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{skuId}")
    // @RequiresPermissions("product:skuinfo:info")
    fun info(@PathVariable("skuId") skuId: Long): R {
        val skuInfo: SkuInfoEntity = skuInfoService.getById(skuId)
        return R.ok().put("skuInfo", skuInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:skuinfo:save")
    fun save(@RequestBody skuInfoEntity: SkuInfoEntity): R {
        skuInfoService.save(skuInfoEntity)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:skuinfo:update")
    fun update(@RequestBody skuInfo: SkuInfoEntity): R {
        skuInfoService.updateById(skuInfo)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:skuinfo:delete")
    fun delete(@RequestBody skuIds: Array<Long>): R {
        skuInfoService.removeByIds(skuIds.asList())
        return R.ok()
    }

    @GetMapping("/{skuId}/price")
    fun getPrice(@PathVariable("skuId") skuId: Long): BigDecimal? {
        return skuInfoService.getById(skuId).price
    }
}