package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.AttrDao
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.service.AttrService


@Service("attrService")
class AttrServiceImpl : ServiceImpl<AttrDao, AttrEntity>() , AttrService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<AttrEntity>().getPage(params),
            QueryWrapper<AttrEntity>()
        )
        return PageUtils(page)
    }
}