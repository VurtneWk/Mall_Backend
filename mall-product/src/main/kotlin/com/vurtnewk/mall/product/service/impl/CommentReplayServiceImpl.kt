package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.CommentReplayDao
import com.vurtnewk.mall.product.entity.CommentReplayEntity
import com.vurtnewk.mall.product.service.CommentReplayService


@Service("commentReplayService")
class CommentReplayServiceImpl : ServiceImpl<CommentReplayDao, CommentReplayEntity>() , CommentReplayService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CommentReplayEntity>().getPage(params),
            QueryWrapper<CommentReplayEntity>()
        )
        return PageUtils(page)
    }
}