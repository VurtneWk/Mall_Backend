package com.vurtnewk.mall.product.service.impl

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.StringUtils
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.utils

import com.vurtnewk.mall.product.dao.AttrGroupDao
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.service.AttrGroupService
import java.util.function.Consumer


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
        return if (catelogId == 0L) {
            KtQueryChainWrapper(AttrGroupEntity::class.java)
                .page(iPage)
                .utils()
        } else {
            //select * from pms_attr_group where catelog_id = ? and (attr_group_id = key or attr_group_name like key)
            val key = params["key"] as String?

            KtQueryChainWrapper(AttrGroupEntity::class.java)
                .eq(AttrGroupEntity::catelogId, catelogId)
                .and(!key.isNullOrEmpty()) {
                    it.eq(AttrGroupEntity::attrGroupId, key)
                        .or().like(AttrGroupEntity::attrGroupName, key)
                }
                .page(iPage)
                .utils()
        }
    }
}