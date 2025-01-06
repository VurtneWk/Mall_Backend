package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.AttrAttrgroupRelationDao
import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.vurtnewk.mall.product.service.AttrAttrgroupRelationService


@Service("attrAttrgroupRelationService")
class AttrAttrgroupRelationServiceImpl : ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity>() , AttrAttrgroupRelationService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<AttrAttrgroupRelationEntity>().getPage(params),
            QueryWrapper<AttrAttrgroupRelationEntity>()
        )
        return PageUtils(page)
    }
}