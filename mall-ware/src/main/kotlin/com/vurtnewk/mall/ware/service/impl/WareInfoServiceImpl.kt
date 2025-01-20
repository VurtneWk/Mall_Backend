package com.vurtnewk.mall.ware.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage

import com.vurtnewk.mall.ware.dao.WareInfoDao
import com.vurtnewk.mall.ware.entity.WareInfoEntity
import com.vurtnewk.mall.ware.service.WareInfoService


@Service("wareInfoService")
class WareInfoServiceImpl : ServiceImpl<WareInfoDao, WareInfoEntity>(), WareInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val key = params["key"] as? String
        return KtQueryChainWrapper(WareInfoEntity::class.java)
            .and(!key.isNullOrBlank()) {
                it.eq(WareInfoEntity::id, key)
                    .or().like(WareInfoEntity::name, key)
                    .or().like(WareInfoEntity::address, key)
                    .or().like(WareInfoEntity::areacode, key)
            }
            .toPage(params)
            .pageUtils()
    }
}