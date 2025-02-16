package com.vurtnewk.mall.ware.controller

import com.vurtnewk.common.excetion.BizCodeEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.vurtnewk.mall.ware.service.WareSkuService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R
import com.vurtnewk.common.utils.R2
import com.vurtnewk.mall.ware.vo.SkuHasStockVo
import com.vurtnewk.mall.ware.vo.WareSkuLockVo

/**
 * 商品库存
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@RestController
@RequestMapping("ware/waresku")
class WareSkuController @Autowired constructor(
    private val wareSkuService: WareSkuService,
) {

    /**
     * 锁定库存
     */
    @PostMapping("/lock/order")
    fun orderLockStock(@RequestBody vo: WareSkuLockVo): R {
        return try {
            wareSkuService.orderLockStock(vo)
            R.ok()
        } catch (e: Exception) {
            R.error(BizCodeEnum.NO_STOCK_EXCEPTION)
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:waresku:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = wareSkuService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("ware:waresku:info")
    fun info(@PathVariable("id") id: Long): R {
        val wareSku: WareSkuEntity = wareSkuService.getById(id)
        return R.ok().put("wareSku", wareSku)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:waresku:save")
    fun save(@RequestBody wareSku: WareSkuEntity): R {
        wareSkuService.save(wareSku)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("ware:waresku:update")
    fun update(@RequestBody wareSku: WareSkuEntity): R {
        wareSkuService.updateById(wareSku)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("ware:waresku:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
        wareSkuService.removeByIds(ids.asList())
        return R.ok()
    }

    /**
     * 查询是否有库存
     */
    @PostMapping("/hasStock")
    fun getSkusHasStock(@RequestBody skuIds: List<Long>): R2<List<SkuHasStockVo>> {
        val vos = wareSkuService.getSkusHasStock(skuIds)
        return R2(vos)
    }
}