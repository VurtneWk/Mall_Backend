package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.vurtnewk.mall.product.service.AttrAttrgroupRelationService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 属性&属性分组关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/attrattrgrouprelation")
class AttrAttrgroupRelationController @Autowired constructor(
        private val attrAttrgroupRelationService: AttrAttrgroupRelationService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attrattrgrouprelation:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = attrAttrgroupRelationService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:attrattrgrouprelation:info")
    fun info(@PathVariable("id") id: Long): R {
        val attrAttrgroupRelation: AttrAttrgroupRelationEntity = attrAttrgroupRelationService.getById(id)
        return R.ok().put("attrAttrgroupRelation", attrAttrgroupRelation)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrattrgrouprelation:save")
    fun save(@RequestBody attrAttrgroupRelation: AttrAttrgroupRelationEntity): R {
            attrAttrgroupRelationService.save(attrAttrgroupRelation)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:attrattrgrouprelation:update")
    fun update(@RequestBody attrAttrgroupRelation: AttrAttrgroupRelationEntity): R {
            attrAttrgroupRelationService.updateById(attrAttrgroupRelation)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:attrattrgrouprelation:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            attrAttrgroupRelationService.removeByIds(ids.asList())
        return R.ok()
    }
}