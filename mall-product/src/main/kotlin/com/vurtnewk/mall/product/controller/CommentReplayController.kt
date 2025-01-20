package com.vurtnewk.mall.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.product.entity.CommentReplayEntity
import com.vurtnewk.mall.product.service.CommentReplayService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 商品评价回复关系
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
@RestController
@RequestMapping("product/commentreplay")
class CommentReplayController @Autowired constructor(
        private val commentReplayService: CommentReplayService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:commentreplay:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = commentReplayService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:commentreplay:info")
    fun info(@PathVariable("id") id: Long): R {
        val commentReplay: CommentReplayEntity = commentReplayService.getById(id)
        return R.ok().put("commentReplay", commentReplay)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:commentreplay:save")
    fun save(@RequestBody commentReplay: CommentReplayEntity): R {
            commentReplayService.save(commentReplay)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:commentreplay:update")
    fun update(@RequestBody commentReplay: CommentReplayEntity): R {
            commentReplayService.updateById(commentReplay)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:commentreplay:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            commentReplayService.removeByIds(ids.asList())
        return R.ok()
    }
}