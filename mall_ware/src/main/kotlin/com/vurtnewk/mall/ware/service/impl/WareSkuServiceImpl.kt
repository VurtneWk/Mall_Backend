package com.vurtnewk.mall.ware.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.logError
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.ware.dao.WareSkuDao
import com.vurtnewk.mall.ware.entity.WareSkuEntity
import com.vurtnewk.mall.ware.feign.ProductFeignService
import com.vurtnewk.mall.ware.service.WareSkuService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("wareSkuService")
class WareSkuServiceImpl(
    private val productFeignService: ProductFeignService,
) : ServiceImpl<WareSkuDao, WareSkuEntity>(), WareSkuService {

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

    @Transactional
    override fun addStock(skuId: Long, wareId: Long, skuNum: Int) {
        //先查表中是否有数据，如果有的话 就修改 没有就新增
        val wareSkuEntities = KtQueryChainWrapper(WareSkuEntity::class.java)
            .eq(WareSkuEntity::skuId, skuId)
            .eq(WareSkuEntity::wareId, wareId)
            .list()

        if (wareSkuEntities.isNullOrEmpty()) {
            val wareSkuEntity = WareSkuEntity().apply {
                this.skuId = skuId
                this.wareId = wareId
                this.stock = skuNum
                this.stockLocked = 0
            }
            //查询名字
            runCatching {
                val r = productFeignService.info(skuId)
                if (r.isSuccess()) {
                    wareSkuEntity.skuName = (r["skuInfo"] as Map<*, *>)["skuName"] as String
                }
            }.onSuccess {
                logInfo("远程查询商品名字成功 ${wareSkuEntity.skuName}")
            }.onFailure {
                logError("远程查询商品名字失败 ${it.message}")
            }
            this.baseMapper.insert(wareSkuEntity)
        } else {
            this.baseMapper.updateStock(skuId, wareId, skuNum)
        }


    }
}