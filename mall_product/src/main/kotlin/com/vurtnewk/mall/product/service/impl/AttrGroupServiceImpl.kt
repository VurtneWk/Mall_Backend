package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.AttrGroupDao
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.service.AttrGroupService


@Service("attrGroupService")
class AttrGroupServiceImpl : ServiceImpl<AttrGroupDao, AttrGroupEntity>() , AttrGroupService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<AttrGroupEntity>().getPage(params),
            QueryWrapper<AttrGroupEntity>()
        )
        return PageUtils(page)
    }
}