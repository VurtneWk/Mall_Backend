package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.CouponSpuRelationEntity
import com.vurtnewk.mall.coupon.service.CouponSpuRelationService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 优惠券与产品关联
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/couponspurelation")
class CouponSpuRelationController @Autowired constructor(
        private val couponSpuRelationService: CouponSpuRelationService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:couponspurelation:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = couponSpuRelationService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:couponspurelation:info")
    fun info(@PathVariable("id") id: Long): R {
        val couponSpuRelation: CouponSpuRelationEntity = couponSpuRelationService.getById(id)
        return R.ok().put("couponSpuRelation", couponSpuRelation)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:couponspurelation:save")
    fun save(@RequestBody couponSpuRelation: CouponSpuRelationEntity): R {
            couponSpuRelationService.save(couponSpuRelation)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:couponspurelation:update")
    fun update(@RequestBody couponSpuRelation: CouponSpuRelationEntity): R {
            couponSpuRelationService.updateById(couponSpuRelation)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:couponspurelation:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            couponSpuRelationService.removeByIds(ids.asList())
        return R.ok()
    }
}