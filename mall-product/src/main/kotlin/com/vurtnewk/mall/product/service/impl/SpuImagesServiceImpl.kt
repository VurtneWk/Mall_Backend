package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.SpuImagesDao
import com.vurtnewk.mall.product.entity.SpuImagesEntity
import com.vurtnewk.mall.product.service.SpuImagesService
import org.springframework.transaction.annotation.Transactional


@Service("spuImagesService")
class SpuImagesServiceImpl : ServiceImpl<SpuImagesDao, SpuImagesEntity>(), SpuImagesService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SpuImagesEntity>().getPage(params),
            QueryWrapper<SpuImagesEntity>()
        )
        return PageUtils(page)
    }

}