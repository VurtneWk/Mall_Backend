package com.vurtnewk.mall.product.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.product.entity.CategoryBrandRelationEntity
import com.vurtnewk.mall.product.service.CategoryBrandRelationService
import com.vurtnewk.mall.product.vo.BrandVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 品牌分类关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/categorybrandrelation")
class CategoryBrandRelationController @Autowired constructor(
    private val categoryBrandRelationService: CategoryBrandRelationService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = categoryBrandRelationService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 获取当前品牌关联的所有分类列表
     */
//    @RequestMapping(value = ["/catelog/list"], method = [RequestMethod.GET])
    @GetMapping("/catelog/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    fun cateloglist(@RequestParam brandId: Long): R {
        val list = KtQueryChainWrapper(CategoryBrandRelationEntity::class.java)
            .eq(CategoryBrandRelationEntity::brandId, brandId)
            .list()
        return R.ok().put("data", list)
    }

    /**
     * 获取分类关联的品牌
     * Controller 处理请求 接口和校验数据
     * Service 接受 Controller 传递的数据， 进行业务处理
     * Controller 接受 Service 处理完的数据 ， 封装页面指定的VO
     */
    @GetMapping("/brands/list")
    fun relationBrandList(@RequestParam(value = "catId", required = true) catId: Long): R {
        // pms_category_brand_relation 表里已有 brandID 和 brandName ，
        // 不过别的查询可能需要全部数据，所以这里又额外查询所有数据
        val categoryBrandRelationEntityList = categoryBrandRelationService.getBrandsByCatId(catId)
        val list = categoryBrandRelationEntityList.map {
            BrandVO(it.brandId, it.name)
        }
        return R.ok().put("data", list)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:categorybrandrelation:info")
    fun info(@PathVariable("id") id: Long): R {
        val categoryBrandRelation: CategoryBrandRelationEntity = categoryBrandRelationService.getById(id)
        return R.ok().put("categoryBrandRelation", categoryBrandRelation)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    fun save(@RequestBody categoryBrandRelation: CategoryBrandRelationEntity): R {
        categoryBrandRelationService.saveDetail(categoryBrandRelation)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:categorybrandrelation:update")
    fun update(@RequestBody categoryBrandRelation: CategoryBrandRelationEntity): R {
        categoryBrandRelationService.updateById(categoryBrandRelation)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:categorybrandrelation:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        categoryBrandRelationService.removeByIds(ids.asList())
        return R.ok()
    }
}