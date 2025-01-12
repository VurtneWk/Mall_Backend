package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.SpuCommentDao
import com.vurtnewk.mall.product.entity.SpuCommentEntity
import com.vurtnewk.mall.product.service.SpuCommentService


@Service("spuCommentService")
class SpuCommentServiceImpl : ServiceImpl<SpuCommentDao, SpuCommentEntity>() , SpuCommentService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SpuCommentEntity>().getPage(params),
            QueryWrapper<SpuCommentEntity>()
        )
        return PageUtils(page)
    }
}