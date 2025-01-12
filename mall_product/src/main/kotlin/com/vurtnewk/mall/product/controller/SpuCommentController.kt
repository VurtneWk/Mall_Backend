package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.SpuCommentEntity
import com.vurtnewk.mall.product.service.SpuCommentService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 商品评价
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-12 04:03:55
 */
@RestController
@RequestMapping("product/spucomment")
class SpuCommentController @Autowired constructor(
        private val spuCommentService: SpuCommentService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:spucomment:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = spuCommentService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:spucomment:info")
    fun info(@PathVariable("id") id: Long): R {
        val spuComment: SpuCommentEntity = spuCommentService.getById(id)
        return R.ok().put("spuComment", spuComment)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spucomment:save")
    fun save(@RequestBody spuComment: SpuCommentEntity): R {
            spuCommentService.save(spuComment)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:spucomment:update")
    fun update(@RequestBody spuComment: SpuCommentEntity): R {
            spuCommentService.updateById(spuComment)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:spucomment:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            spuCommentService.removeByIds(ids.asList())
        return R.ok()
    }
}