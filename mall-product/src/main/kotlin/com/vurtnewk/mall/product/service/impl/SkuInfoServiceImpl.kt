package com.vurtnewk.mall.product.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.product.config.MyThreadConfig
import com.vurtnewk.mall.product.dao.SkuInfoDao
import com.vurtnewk.mall.product.entity.SkuInfoEntity
import com.vurtnewk.mall.product.service.*
import com.vurtnewk.mall.product.vo.SkuItemVo
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ThreadPoolExecutor


@Service("skuInfoService")
class SkuInfoServiceImpl(
    private val mSkuImagesService: SkuImagesService,
    private val mSpuInfoDescService: SpuInfoDescService,
    private val mAttrGroupService: AttrGroupService,
    private val mSkuSaleAttrValueService: SkuSaleAttrValueService,
    private val executor: ThreadPoolExecutor,
) : ServiceImpl<SkuInfoDao, SkuInfoEntity>(), SkuInfoService {

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

    /**
     * 需要获取的数据信息
     * 1. sku基本信息 pms_sku_info
     * 2. sku图片信息 pms_sku_images
     * 3. sku对应的spu销售属性组合
     * 4. sku介绍  spu数据 pms_spu_info
     * 5. sku规格参数信息
     */
    override fun queryItem(skuId: Long): SkuItemVo? {
        val skuItemVo = SkuItemVo()
        // 1. sku基本信息 pms_sku_info
        val infoFuture = CompletableFuture.supplyAsync(
            {
                skuItemVo.info = getById(skuId)
                return@supplyAsync skuItemVo.info
            }, executor
        )
        //2. sku图片信息 pms_sku_images
        val imageFuture = CompletableFuture.runAsync({
            skuItemVo.images = mSkuImagesService.getImagesById(skuId)
        }, executor)

        //可能查询一个数据库里不存在skuId
        val infoGet = infoFuture.get()
        infoGet ?: return null

        //3. sku对应的spu销售属性组合
        val saleAttrFuture = infoFuture.thenAcceptAsync({ info ->
            skuItemVo.saleAttr = mSkuSaleAttrValueService.getSaleAttrsBySpuId(info!!.spuId!!)
        }, executor)
        //4. sku介绍  spu数据 pms_spu_info
        val descFuture = infoFuture.thenAcceptAsync({ info ->
            skuItemVo.desc = mSpuInfoDescService.getById(info!!.spuId!!)
        }, executor)
        //5. sku规格参数信息
        val baseAttrFuture = infoFuture.thenAcceptAsync({ info ->
            skuItemVo.groupAttrs = mAttrGroupService.getAttrGroupWithAttrsBySpuId(info!!.spuId!!, info!!.catalogId!!)
        }, executor)

        CompletableFuture.allOf(infoFuture, saleAttrFuture, descFuture, baseAttrFuture, imageFuture).get()

        logInfo("queryItem $skuItemVo")
        return skuItemVo
    }

}