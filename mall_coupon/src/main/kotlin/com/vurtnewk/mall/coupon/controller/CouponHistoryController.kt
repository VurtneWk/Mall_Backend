package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.CouponHistoryEntity
import com.vurtnewk.mall.coupon.service.CouponHistoryService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 优惠券领取历史记录
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/couponhistory")
class CouponHistoryController @Autowired constructor(
        private val couponHistoryService: CouponHistoryService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:couponhistory:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = couponHistoryService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:couponhistory:info")
    fun info(@PathVariable("id") id: Long): R {
        val couponHistory: CouponHistoryEntity = couponHistoryService.getById(id)
        return R.ok().put("couponHistory", couponHistory)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:couponhistory:save")
    fun save(@RequestBody couponHistory: CouponHistoryEntity): R {
            couponHistoryService.save(couponHistory)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:couponhistory:update")
    fun update(@RequestBody couponHistory: CouponHistoryEntity): R {
            couponHistoryService.updateById(couponHistory)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:couponhistory:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            couponHistoryService.removeByIds(ids.asList())
        return R.ok()
    }
}