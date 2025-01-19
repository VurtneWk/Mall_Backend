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
import com.vurtnewk.mall.product.service.AttrGroupService
import com.vurtnewk.mall.product.service.SkuImagesService
import com.vurtnewk.mall.product.service.SkuInfoService
import com.vurtnewk.mall.product.service.SpuInfoDescService
import com.vurtnewk.mall.product.vo.SkuItemVo
import org.springframework.stereotype.Service


@Service("skuInfoService")
class SkuInfoServiceImpl(
    private val mSkuImagesService: SkuImagesService,
    private val mSpuInfoDescService: SpuInfoDescService,
    private val mAttrGroupService: AttrGroupService
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
    override fun queryItem(skuId: Long): SkuItemVo {
        val skuItemVo = SkuItemVo()
        //region 1. sku基本信息 pms_sku_info
        skuItemVo.skuInfoEntity = getById(skuId)
        //如果压根没查到数据 直接返回
        skuItemVo.skuInfoEntity ?: return skuItemVo

        val spuId = skuItemVo.skuInfoEntity!!.spuId!!
        val catalogId = skuItemVo.skuInfoEntity!!.catalogId!!
        //endregion

        //region 2. sku图片信息 pms_sku_images
        skuItemVo.images = mSkuImagesService.getImagesById(skuId)
        //endregion

        //region 3. sku对应的spu销售属性组合
        // pms_product_attr_value 通过 spuId 能获取有哪些属性
        // pms_attr 能获取是 attr_id 是否是销售属性
        // 根据 attr_id 能在 pms_attr_attrgroup_relation 获取 对应的组
        // pms_attr_group 获取 组名信息


        //endregion

        //region 4. sku介绍  spu数据 pms_spu_info
        skuItemVo.desc = mSpuInfoDescService.getById(spuId)
        //endregion

        //region 5. sku规格参数信息
        mAttrGroupService.getAttrGroupWithAttrsBySpuId(spuId , catalogId)

        
        //endregion
        
        
        return SkuItemVo()
    }


}