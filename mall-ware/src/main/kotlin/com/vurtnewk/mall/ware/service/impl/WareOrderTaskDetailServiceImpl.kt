package com.vurtnewk.mall.ware.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.ware.dao.WareOrderTaskDetailDao
import com.vurtnewk.mall.ware.entity.WareOrderTaskDetailEntity
import com.vurtnewk.mall.ware.service.WareOrderTaskDetailService


@Service("wareOrderTaskDetailService")
class WareOrderTaskDetailServiceImpl : ServiceImpl<WareOrderTaskDetailDao, WareOrderTaskDetailEntity>() , WareOrderTaskDetailService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<WareOrderTaskDetailEntity>().getPage(params),
            QueryWrapper<WareOrderTaskDetailEntity>()
        )
        return PageUtils(page)
    }
}