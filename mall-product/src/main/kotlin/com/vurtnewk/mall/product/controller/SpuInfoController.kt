package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.SpuInfoEntity
import com.vurtnewk.mall.product.service.SpuInfoService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.mall.product.vo.SpuInfoVO

/**
 * spu信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@RestController
@RequestMapping("product/spuinfo")
class SpuInfoController @Autowired constructor(
    private val spuInfoService: SpuInfoService,
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:spuinfo:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = spuInfoService.queryPageByCondition(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:spuinfo:info")
    fun info(@PathVariable("id") id: Long): R {
        val spuInfo: SpuInfoEntity = spuInfoService.getById(id)
        return R.ok().put("spuInfo", spuInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    fun save(@RequestBody vo: SpuInfoVO): R {
        spuInfoService.saveSpuInfo(vo)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:spuinfo:update")
    fun update(@RequestBody spuInfo: SpuInfoEntity): R {
        spuInfoService.updateById(spuInfo)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:spuinfo:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        spuInfoService.removeByIds(ids.asList())
        return R.ok()
    }

    /**
     * ## 商品上架功能
     * /product/spuinfo/{spuId}/up
     */
    @PostMapping("/{spuId}/up")
    fun spuUp(@PathVariable("spuId") spuId: Long): R {
        spuInfoService.spuUp(spuId)
        return R.ok()
    }

    /**
     * ## 根据SkuId获取Spu信息
     */
    @GetMapping("/skuId/{skuId}")
    fun getSpuInfoBySkuId(@PathVariable("skuId") skuId: Long): R {
        val data = spuInfoService.getSpuInfoBySkuId(skuId)
        println("getSpuInfoBySkuId ==> $data")
        return R.ok().putData(data)
    }
}