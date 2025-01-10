package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.service.AttrGroupService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.product.service.CategoryService

/**
 * 属性分组
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/attrgroup")
class AttrGroupController @Autowired constructor(
    private val attrGroupService: AttrGroupService,
    private val categoryService: CategoryService
) {

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    fun list(
        @RequestParam params: Map<String, Any>,
        @PathVariable("catelogId") catelogId: Long
    ): R {
//        val page: PageUtils = attrGroupService.queryPage(params)
        val page: PageUtils = attrGroupService.queryPage(params, catelogId)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    // @RequiresPermissions("product:attrgroup:info")
    fun info(@PathVariable("attrGroupId") attrGroupId: Long): R {
        val attrGroup: AttrGroupEntity = attrGroupService.getById(attrGroupId)
            .apply {
                catelogId?.let {
                    //查出当前这个catelogId的所有父级ID
                    this.catelogPath = categoryService.findCatelogPath(it)
                }
            }
        return R.ok().put("attrGroup", attrGroup)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    fun save(@RequestBody attrGroup: AttrGroupEntity): R {
        attrGroupService.save(attrGroup)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:attrgroup:update")
    fun update(@RequestBody attrGroup: AttrGroupEntity): R {
        attrGroupService.updateById(attrGroup)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:attrgroup:delete")
    fun delete(@RequestBody attrGroupIds: Array<Long>): R {
        attrGroupService.removeByIds(attrGroupIds.asList())
        return R.ok()
    }
}