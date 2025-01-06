package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.ProductAttrValueEntity
import com.vurtnewk.mall.product.service.ProductAttrValueService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * spu属性值
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/productattrvalue")
class ProductAttrValueController @Autowired constructor(
        private val productAttrValueService: ProductAttrValueService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productattrvalue:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = productAttrValueService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:productattrvalue:info")
    fun info(@PathVariable("id") id: Long): R {
        val productAttrValue: ProductAttrValueEntity = productAttrValueService.getById(id)
        return R.ok().put("productAttrValue", productAttrValue)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productattrvalue:save")
    fun save(@RequestBody productAttrValue: ProductAttrValueEntity): R {
            productAttrValueService.save(productAttrValue)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:productattrvalue:update")
    fun update(@RequestBody productAttrValue: ProductAttrValueEntity): R {
            productAttrValueService.updateById(productAttrValue)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:productattrvalue:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            productAttrValueService.removeByIds(ids.asList())
        return R.ok()
    }
}