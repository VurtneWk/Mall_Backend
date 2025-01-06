package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.CouponSpuCategoryRelationEntity
import com.vurtnewk.mall.coupon.service.CouponSpuCategoryRelationService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 优惠券分类关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/couponspucategoryrelation")
class CouponSpuCategoryRelationController @Autowired constructor(
        private val couponSpuCategoryRelationService: CouponSpuCategoryRelationService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:couponspucategoryrelation:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = couponSpuCategoryRelationService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:couponspucategoryrelation:info")
    fun info(@PathVariable("id") id: Long): R {
        val couponSpuCategoryRelation: CouponSpuCategoryRelationEntity = couponSpuCategoryRelationService.getById(id)
        return R.ok().put("couponSpuCategoryRelation", couponSpuCategoryRelation)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:couponspucategoryrelation:save")
    fun save(@RequestBody couponSpuCategoryRelation: CouponSpuCategoryRelationEntity): R {
            couponSpuCategoryRelationService.save(couponSpuCategoryRelation)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:couponspucategoryrelation:update")
    fun update(@RequestBody couponSpuCategoryRelation: CouponSpuCategoryRelationEntity): R {
            couponSpuCategoryRelationService.updateById(couponSpuCategoryRelation)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:couponspucategoryrelation:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            couponSpuCategoryRelationService.removeByIds(ids.asList())
        return R.ok()
    }
}