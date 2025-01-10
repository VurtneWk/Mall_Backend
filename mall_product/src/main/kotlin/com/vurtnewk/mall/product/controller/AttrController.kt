package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.service.AttrService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.product.vo.AttrVO

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

    // /base/list/0?t=1736503492495&page=1&limit=10&key=1
    @RequestMapping("/base/list/{catelogId}")
    fun baseAttrList(@RequestParam params: Map<String, Any>, @PathVariable("catelogId") catelogId: Long): R {
        val page: PageUtils = attrService.queryBaseAttrPage(params, catelogId)
        return R.ok().put("page", page)
    }

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
//        val attr: AttrEntity = attrService.getById(attrId)
        val attrRespVO = attrService.getAttrInfo(attrId)
        return R.ok().put("attr", attrRespVO)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    fun save(@RequestBody attr: AttrVO): R {
        attrService.saveAttrVO(attr)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:attr:update")
    fun update(@RequestBody attr: AttrVO): R {
//        attrService.updateById(attr)
        attrService.updateAttrVO(attr)
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