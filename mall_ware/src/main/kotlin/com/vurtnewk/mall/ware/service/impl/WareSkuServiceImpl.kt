package com.vurtnewk.mall.ware.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.ware.dao.WareSkuDao
import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.vurtnewk.mall.ware.service.WareSkuService
import org.springframework.stereotype.Service


@Service("wareSkuService")
class WareSkuServiceImpl : ServiceImpl<WareSkuDao, WareSkuEntity>(), WareSkuService {

    /**
     * ## 查询商品库存
     * ### 参数
     * ```json
     * {
     *    page: 1,//当前页码
     *    limit: 10,//每页记录数
     *    sidx: 'id',//排序字段
     *    order: 'asc/desc',//排序方式
     *    wareId: 123,//仓库id
     *    skuId: 123//商品id
     * }
     * ```
     */
    override fun queryPage(params: Map<String, Any>): PageUtils {
        val wareId = params["wareId"] as? String
        val skuId = params["skuId"] as? String
        return KtQueryChainWrapper(WareSkuEntity::class.java)
            .eq(!wareId.isNullOrBlank(), WareSkuEntity::wareId, wareId)
            .eq(!skuId.isNullOrBlank(), WareSkuEntity::skuId, skuId)
            .toPage(params)
            .pageUtils()
    }
}