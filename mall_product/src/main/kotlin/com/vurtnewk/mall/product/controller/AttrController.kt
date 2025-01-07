package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.service.AttrService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 商品属性
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/attr")
class AttrController @Autowired constructor(
        private val attrService: AttrService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = attrService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    // @RequiresPermissions("product:attr:info")
    fun info(@PathVariable("attrId") attrId: Long): R {
        val attr: AttrEntity = attrService.getById(attrId)
        return R.ok().put("attr", attr)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    fun save(@RequestBody attr: AttrEntity): R {
            attrService.save(attr)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:attr:update")
    fun update(@RequestBody attr: AttrEntity): R {
            attrService.updateById(attr)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:attr:delete")
    fun delete(@RequestBody attrIds: Array<Long>): R {
            attrService.removeByIds(attrIds.asList())
        return R.ok()
    }
}