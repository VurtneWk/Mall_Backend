package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.SpuInfoDao
import com.vurtnewk.mall.product.entity.*
import com.vurtnewk.mall.product.service.*
import com.vurtnewk.mall.product.vo.SpuInfoVO
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service("spuInfoService")
class SpuInfoServiceImpl(
    private val mSpuInfoDescService: SpuInfoDescService,
    private val mSpuImagesService: SpuImagesService,
    private val mAttrService: AttrService,
    private val mProductAttrValueService: ProductAttrValueService
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
        this.saveBaseSpuInfo(spuInfoEntity)

        //2. 保存spu的描述图片 pms_spu_info_desc
        val spuInfoDescEntity = SpuInfoDescEntity()
        spuInfoDescEntity.spuId = spuInfoEntity.id
        spuInfoDescEntity.decript = spuInfoVO.decript.joinToString()
        mSpuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity)

        //3. 保存spu的图片集 pms_spu_images
        //这里为什么不构建后直接调用Service的saveBatch ?
        mSpuImagesService.saveImages(spuInfoEntity.id!!, spuInfoVO.images)

        //region 4. 保存spu的规格参数 pms_product_attr_value
        val baseAttrsList  = spuInfoVO.baseAttrs?.mapNotNull { attr ->
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


//        5.保存spu的积分信息：mall_sms -> sms_spu_bounds

//        6.保存当前spu对应的sku信息
//        - sku的基本信息 pms_sku_info
//        - sku的图片信息 pms_sku_images
//        - sku的销售属性信息 pms_sku_sale_attr_value
//        - sku的优惠、满减等信息 mall_sms -> sms_sku_ladder 、sms_sku_full_reduction 、sms_member_price
    }


    override fun saveBaseSpuInfo(spuInfoEntity: SpuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity)
    }

}