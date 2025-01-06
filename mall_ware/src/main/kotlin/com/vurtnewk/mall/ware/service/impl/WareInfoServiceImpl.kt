package com.vurtnewk.mall.ware.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.ware.dao.WareInfoDao
import com.vurtnewk.mall.ware.entity.WareInfoEntity
import com.vurtnewk.mall.ware.service.WareInfoService


@Service("wareInfoService")
class WareInfoServiceImpl : ServiceImpl<WareInfoDao, WareInfoEntity>() , WareInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<WareInfoEntity>().getPage(params),
            QueryWrapper<WareInfoEntity>()
        )
        return PageUtils(page)
    }
}