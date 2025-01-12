package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.SpuInfoDescDao
import com.vurtnewk.mall.product.entity.SpuInfoDescEntity
import com.vurtnewk.mall.product.service.SpuInfoDescService
import com.vurtnewk.mall.product.vo.SpuInfoVO


@Service("spuInfoDescService")
class SpuInfoDescServiceImpl : ServiceImpl<SpuInfoDescDao, SpuInfoDescEntity>(), SpuInfoDescService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SpuInfoDescEntity>().getPage(params),
            QueryWrapper<SpuInfoDescEntity>()
        )
        return PageUtils(page)
    }

    override fun saveSpuInfoDesc(spuInfoDescEntity: SpuInfoDescEntity) {
        this.baseMapper.insert(spuInfoDescEntity)
    }
}