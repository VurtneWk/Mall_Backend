package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.service.AttrGroupService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.product.service.AttrAttrgroupRelationService
import com.vurtnewk.mall.product.service.CategoryService
import com.vurtnewk.mall.product.vo.AttrGroupRelationVO

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
    private val attrGroupService: AttrGroupService, private val categoryService: CategoryService,
    private val attrgroupRelationService: AttrAttrgroupRelationService
) {

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    fun list(@RequestParam params: Map<String, Any>, @PathVariable("catelogId") catelogId: Long): R {
//        val page: PageUtils = attrGroupService.queryPage(params)
        val page: PageUtils = attrGroupService.queryPage(params, catelogId)
        return R.ok().put("page", page)
    }

    /**
     * 查询指定 attrgroupId 下所有关联的属性
     */
    @RequestMapping("{attrgroupId}/attr/relation")
    fun attrGroupRelation(@PathVariable("attrgroupId") attrgroupId: Long): R {
        val list = attrGroupService.getAttrGrouprelation(attrgroupId)
        return R.ok().put("data", list)
    }

    /**
     * 删除关联的属性
     * @param attrGroupRelationVOList 要删除的关联的属性集合
     */
    @RequestMapping("attr/relation/delete")
    fun deleteRelation(@RequestBody attrGroupRelationVOList: List<AttrGroupRelationVO>): R {
        attrGroupService.deleteRelation(attrGroupRelationVOList)
        return R.ok()
    }

    /**
     * 指定 attrGroupId 下没有关联的属性
     */
    @RequestMapping("{attrGroupId}/noattr/relation")
    fun attrNotRelation(
        @RequestParam params: Map<String, Any>,
        @PathVariable("attrGroupId") attrGroupId: Long
    ): R {
        val page = attrGroupService.attrNotRelation(params, attrGroupId)
        return R.ok().put("page", page)
    }

    /**
     * 新增关联关系
     */
    @PostMapping("attr/relation")
    fun addRelation(@RequestBody attrGroupRelationVOList: List<AttrGroupRelationVO>): R {
        attrgroupRelationService.saveBatch(attrGroupRelationVOList)
        return R.ok()
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    // @RequiresPermissions("product:attrgroup:info")
    fun info(@PathVariable("attrGroupId") attrGroupId: Long): R {
        val attrGroup: AttrGroupEntity = attrGroupService.getById(attrGroupId).apply {
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