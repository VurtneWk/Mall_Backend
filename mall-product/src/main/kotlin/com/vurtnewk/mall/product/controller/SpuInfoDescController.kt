package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.SpuInfoDescEntity
import com.vurtnewk.mall.product.service.SpuInfoDescService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.product.vo.SpuInfoVO

/**
 * spu信息介绍
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@RestController
@RequestMapping("product/spuinfodesc")
class SpuInfoDescController @Autowired constructor(
    private val spuInfoDescService: SpuInfoDescService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:spuinfodesc:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = spuInfoDescService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{spuId}")
    // @RequiresPermissions("product:spuinfodesc:info")
    fun info(@PathVariable("spuId") spuId: Long): R {
        val spuInfoDesc: SpuInfoDescEntity = spuInfoDescService.getById(spuId)
        return R.ok().put("spuInfoDesc", spuInfoDesc)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spuinfodesc:save")
    fun save(@RequestBody spuInfoDesc: SpuInfoDescEntity): R {
        spuInfoDescService.save(spuInfoDesc)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:spuinfodesc:update")
    fun update(@RequestBody spuInfoDesc: SpuInfoDescEntity): R {
        spuInfoDescService.updateById(spuInfoDesc)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:spuinfodesc:delete")
    fun delete(@RequestBody spuIds: Array<Long>): R {
        spuInfoDescService.removeByIds(spuIds.asList())
        return R.ok()
    }
}