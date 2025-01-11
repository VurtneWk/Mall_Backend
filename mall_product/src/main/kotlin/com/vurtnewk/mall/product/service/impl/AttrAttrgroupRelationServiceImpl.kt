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
import com.vurtnewk.mall.product.vo.AttrGroupRelationVO
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional


@Service("attrAttrgroupRelationService")
class AttrAttrgroupRelationServiceImpl : ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity>(), AttrAttrgroupRelationService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<AttrAttrgroupRelationEntity>().getPage(params),
            QueryWrapper<AttrAttrgroupRelationEntity>()
        )
        return PageUtils(page)
    }

    @Transactional
    override fun saveBatch(attrGroupRelationVOList: List<AttrGroupRelationVO>) {
        val list = attrGroupRelationVOList
            .map {
                val relationEntity = AttrAttrgroupRelationEntity()
                BeanUtils.copyProperties(it, relationEntity)
                relationEntity
            }
        this.saveBatch(list)
    }
}