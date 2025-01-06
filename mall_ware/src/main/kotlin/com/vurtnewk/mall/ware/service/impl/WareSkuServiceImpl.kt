package com.vurtnewk.mall.ware.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.ware.dao.WareSkuDao
import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.vurtnewk.mall.ware.service.WareSkuService


@Service("wareSkuService")
class WareSkuServiceImpl : ServiceImpl<WareSkuDao, WareSkuEntity>() , WareSkuService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<WareSkuEntity>().getPage(params),
            QueryWrapper<WareSkuEntity>()
        )
        return PageUtils(page)
    }
}