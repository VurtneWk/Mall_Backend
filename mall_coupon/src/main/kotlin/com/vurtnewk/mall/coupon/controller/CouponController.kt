package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.CouponEntity
import com.vurtnewk.mall.coupon.service.CouponService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 优惠券信息
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/coupon")
class CouponController @Autowired constructor(
        private val couponService: CouponService
) {

    @RequestMapping("/member/list")
    fun memberCoupons(): R{
        val couponEntity = CouponEntity()
        couponEntity.couponName = "满100减10"
        return R.ok().put("coupons", listOf(couponEntity))
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:coupon:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = couponService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:coupon:info")
    fun info(@PathVariable("id") id: Long): R {
        val coupon: CouponEntity = couponService.getById(id)
        return R.ok().put("coupon", coupon)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:coupon:save")
    fun save(@RequestBody coupon: CouponEntity): R {
            couponService.save(coupon)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:coupon:update")
    fun update(@RequestBody coupon: CouponEntity): R {
            couponService.updateById(coupon)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:coupon:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            couponService.removeByIds(ids.asList())
        return R.ok()
    }
}