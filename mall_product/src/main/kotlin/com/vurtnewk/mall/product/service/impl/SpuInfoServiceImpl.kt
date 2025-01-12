package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.dto.SkuReductionDto
import com.vurtnewk.common.dto.SpuBoundsDto
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.logError

import com.vurtnewk.mall.product.dao.SpuInfoDao
import com.vurtnewk.mall.product.entity.*
import com.vurtnewk.mall.product.feign.CouponFeignService
import com.vurtnewk.mall.product.service.*
import com.vurtnewk.mall.product.vo.SpuInfoVO
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*


@Service("spuInfoService")
class SpuInfoServiceImpl(
    private val mSpuInfoDescService: SpuInfoDescService,
    private val mSpuImagesService: SpuImagesService,
    private val mAttrService: AttrService,
    private val mProductAttrValueService: ProductAttrValueService,
    private val mSkuInfoService: SkuInfoService,
    private val mSkuImagesService: SkuImagesService,
    private val mSkuSaleAttrValueService: SkuSaleAttrValueService,
    private val mCouponFeignService: CouponFeignService
) : ServiceImpl<SpuInfoDao, SpuInfoEntity>(), SpuInfoService {


    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SpuInfoEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    /**
     * ## 商品保存的基本步骤
     *  1. 保存spu基本信息 pms_spu_info
     *  2. 保存spu的描述图片 pms_spu_info_desc
     *  3. 保存spu的图片集 pms_spu_images
     *  4. 保存spu的规格参数 pms_product_attr_value
     *  5. 保存spu的积分信息：mall_sms -> sms_spu_bounds
     *  6. 保存当前spu对应的sku信息
     *      - sku的基本信息 pms_sku_info
     *      - sku的图片信息 pms_sku_images
     *      - sku的销售属性信息 pms_sku_sale_attr_value
     *      - sku的优惠、满减等信息 mall_sms -> sms_sku_ladder 、sms_sku_full_reduction 、sms_member_price
     */
    @Transactional
    override fun saveSpuInfo(spuInfoVO: SpuInfoVO) {
        //1. 保存spu基本信息 pms_spu_info
        val spuInfoEntity = SpuInfoEntity()
            .apply {
                createTime = Date()
                updateTime = Date()
            }
        BeanUtils.copyProperties(spuInfoVO, spuInfoEntity)
        this.save(spuInfoEntity)

        //region 2. 保存spu的描述图片 pms_spu_info_desc
        val spuInfoDescEntity = SpuInfoDescEntity()
        spuInfoDescEntity.spuId = spuInfoEntity.id
        spuInfoDescEntity.decript = spuInfoVO.decript.joinToString()
        mSpuInfoDescService.save(spuInfoDescEntity)
        //endregion


        //region 3. 保存spu的图片集 pms_spu_images
        val spuImagesEntities = spuInfoVO.images.map {
            SpuImagesEntity().apply {
                spuId = spuInfoEntity.id
                imgUrl = it
            }
        }
        mSpuImagesService.saveBatch(spuImagesEntities)
        //endregion


        //region 4. 保存spu的规格参数 pms_product_attr_value
        val baseAttrsList = spuInfoVO.baseAttrs?.mapNotNull { attr ->
            attr ?: return@mapNotNull null
            val productAttrValueEntity = ProductAttrValueEntity()
                .apply {
                    attrId = attr.attrId
                    attrValue = attr.attrValues
                    quickShow = attr.showDesc
                    spuId = spuInfoEntity.id
                    attrName = mAttrService.getById(attr.attrId).attrName
                }
            productAttrValueEntity
        }
        mProductAttrValueService.saveBatch(baseAttrsList)
        //endregion

        //region 5.保存spu的积分信息：mall_sms -> sms_spu_bounds
        spuInfoVO.bounds?.let {
            val spuBoundsDto = SpuBoundsDto()
            BeanUtils.copyProperties(it, spuBoundsDto)
            spuBoundsDto.spuId = spuInfoEntity.id
            val r = mCouponFeignService.saveSpuBounds(spuBoundsDto)
            if (!r.isSuccess()) {
                logError("远程保存spu的积分信息失败")
            }
            r
        }
        //endregion


        //region 6.保存当前spu对应的sku信息
        spuInfoVO.skus?.forEach { sku ->
            sku ?: return@forEach
            // 6.1 sku的基本信息 pms_sku_info
            val skuInfoEntity = SkuInfoEntity()
            BeanUtils.copyProperties(sku, skuInfoEntity)
            with(skuInfoEntity) {
                this.brandId = spuInfoEntity.brandId
                this.catalogId = spuInfoEntity.catalogId
                this.saleCount = 0
                this.spuId = spuInfoEntity.id
                this.skuDefaultImg = sku.images?.firstOrNull { it?.defaultImg == 1 }?.imgUrl
            }
            // 因为图片存储时需要用到 skuInfoEntity id ， 所以需要单个存储
            mSkuInfoService.save(skuInfoEntity)

            //6.2 sku的图片信息 pms_sku_images
            sku.images?.mapNotNull { image ->
                image ?: return@mapNotNull null
                val skuImagesEntity = SkuImagesEntity()
                skuImagesEntity.skuId = skuInfoEntity.skuId
                skuImagesEntity.imgUrl = image.imgUrl
                skuImagesEntity.defaultImg = image.defaultImg
                skuImagesEntity//
            }?.filter {
                !it.imgUrl.isNullOrEmpty()
            }?.let {
                mSkuImagesService.saveBatch(it)
            }

            //6.3 sku的销售属性信息 pms_sku_sale_attr_value
            sku.attr?.mapNotNull { attr ->
                attr ?: return@mapNotNull null
                val skuSaleAttrValueEntity = SkuSaleAttrValueEntity()
                BeanUtils.copyProperties(attr, skuSaleAttrValueEntity)
                skuSaleAttrValueEntity.skuId = skuInfoEntity.skuId
                skuSaleAttrValueEntity
            }?.let {
                mSkuSaleAttrValueService.saveBatch(it)
            }

            //6.4 sku的优惠、满减等信息 mall_sms -> sms_sku_ladder 、sms_sku_full_reduction 、sms_member_price
            val skuReductionDto = SkuReductionDto()
            BeanUtils.copyProperties(sku, skuReductionDto)
            skuReductionDto.skuId = skuInfoEntity.skuId
            if ((skuReductionDto.fullCount ?: 0) > 0 || (skuReductionDto.fullPrice?.compareTo(BigDecimal.ZERO) ?: 0) > 0) {
                mCouponFeignService.saveSkuReduction(skuReductionDto).apply {
                    if (!this.isSuccess()) {
                        logError("远程保存sku的优惠、满减等信息失败")
                    }
                }
            }
        }
        //endregion
    }

}