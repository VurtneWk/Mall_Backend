package com.vurtnewk.mall.coupon.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.coupon.entity.SeckillSkuNoticeEntity
import com.vurtnewk.mall.coupon.service.SeckillSkuNoticeService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 秒杀商品通知订阅
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:24:58
 */
@RestController
@RequestMapping("coupon/seckillskunotice")
class SeckillSkuNoticeController @Autowired constructor(
        private val seckillSkuNoticeService: SeckillSkuNoticeService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:seckillskunotice:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = seckillSkuNoticeService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:seckillskunotice:info")
    fun info(@PathVariable("id") id: Long): R {
        val seckillSkuNotice: SeckillSkuNoticeEntity = seckillSkuNoticeService.getById(id)
        return R.ok().put("seckillSkuNotice", seckillSkuNotice)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:seckillskunotice:save")
    fun save(@RequestBody seckillSkuNotice: SeckillSkuNoticeEntity): R {
            seckillSkuNoticeService.save(seckillSkuNotice)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:seckillskunotice:update")
    fun update(@RequestBody seckillSkuNotice: SeckillSkuNoticeEntity): R {
            seckillSkuNoticeService.updateById(seckillSkuNotice)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:seckillskunotice:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            seckillSkuNoticeService.removeByIds(ids.asList())
        return R.ok()
    }
}