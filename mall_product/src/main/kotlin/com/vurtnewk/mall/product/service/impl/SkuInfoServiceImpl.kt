package com.vurtnewk.mall.product.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.product.dao.SkuInfoDao
import com.vurtnewk.mall.product.entity.SkuInfoEntity
import com.vurtnewk.mall.product.service.SkuInfoService
import org.springframework.stereotype.Service


@Service("skuInfoService")
class SkuInfoServiceImpl : ServiceImpl<SkuInfoDao, SkuInfoEntity>(), SkuInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuInfoEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    /**
     * # sku检索
     * ### 请求数据
     * ```json
     * {
     * 	page: 1,//当前页码
     * 	limit: 10,//每页记录数
     * 	sidx: 'id',//排序字段
     * 	order: 'asc/desc',//排序方式
     * 	key: '华为',//检索关键字
     * 	catelogId: 0,
     * 	brandId: 0,
     * 	min: 0,
     * 	max: 0
     * }
     * ```
     */
    override fun queryPageByCondition(params: Map<String, Any>): PageUtils {
        val key = params["key"] as? String
        val catalogId = params["catelogId"] as? String
        val brandId = params["brandId"] as? String
        val min = params["min"] as? String
        val max = params["max"] as? String

        return KtQueryChainWrapper(SkuInfoEntity::class.java)
            .and(!key.isNullOrBlank()) {
                it.eq(SkuInfoEntity::skuId, key).or().like(SkuInfoEntity::skuName, key)
            }
            .eq(!catalogId.isNullOrBlank() && catalogId != "0", SkuInfoEntity::catalogId, catalogId)
            .eq(!brandId.isNullOrBlank() && brandId != "0", SkuInfoEntity::brandId, brandId)
            .ge(!min.isNullOrBlank() && min != "0", SkuInfoEntity::price, min) //ge : greater than or equal to
            .le(!max.isNullOrBlank() && max != "0", SkuInfoEntity::price, max)
            .toPage(params)
            .pageUtils()
    }

    override fun getSkusBySpuId(spuId: Long): List<SkuInfoEntity> {
        return KtQueryChainWrapper(SkuInfoEntity::class.java)
            .eq(SkuInfoEntity::spuId, spuId)
            .list()
    }


}