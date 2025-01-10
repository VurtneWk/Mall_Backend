package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.pageUtils

import com.vurtnewk.mall.product.dao.AttrGroupDao
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.service.AttrGroupService


@Service("attrGroupService")
class AttrGroupServiceImpl : ServiceImpl<AttrGroupDao, AttrGroupEntity>(), AttrGroupService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<AttrGroupEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    override fun queryPage(params: Map<String, Any>, catelogId: Long): PageUtils {
        val iPage = Query<AttrGroupEntity>().getPage(params)
        val key = params["key"] as String?
        //select * from pms_attr_group where catelog_id = ? and (attr_group_id = key or attr_group_name like key)
        val ktQueryChainWrapper = KtQueryChainWrapper(AttrGroupEntity::class.java)
            .and(!key.isNullOrEmpty()) {
                it.eq(AttrGroupEntity::attrGroupId, key)
                    .or().like(AttrGroupEntity::attrGroupName, key)
            }
        if (catelogId != 0L) {
            ktQueryChainWrapper.eq(AttrGroupEntity::catelogId, catelogId)
        }
        return ktQueryChainWrapper.page(iPage).pageUtils()
    }
}