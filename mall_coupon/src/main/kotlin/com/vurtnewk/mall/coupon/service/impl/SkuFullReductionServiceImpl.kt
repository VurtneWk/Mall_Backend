package com.vurtnewk.mall.coupon.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.dto.SkuReductionDto
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.coupon.dao.SkuFullReductionDao
import com.vurtnewk.mall.coupon.entity.MemberPriceEntity
import com.vurtnewk.mall.coupon.entity.SkuFullReductionEntity
import com.vurtnewk.mall.coupon.entity.SkuLadderEntity
import com.vurtnewk.mall.coupon.service.MemberPriceService
import com.vurtnewk.mall.coupon.service.SkuFullReductionService
import com.vurtnewk.mall.coupon.service.SkuLadderService
import org.springframework.beans.BeanUtils


@Service("skuFullReductionService")
class SkuFullReductionServiceImpl(
    private val skuLadderService: SkuLadderService,
    private val memberPriceService: MemberPriceService
) : ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity>(), SkuFullReductionService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuFullReductionEntity>().getPage(params),
            QueryWrapper<SkuFullReductionEntity>()
        )
        return PageUtils(page)
    }

    /**
     * production 商品提交时 远程调用该方法
     * 6.4 sku的优惠、满减等信息 mall_sms -> sms_sku_ladder 、sms_sku_full_reduction 、sms_member_price
     */
    override fun saveSkuReduction(skuReductionDto: SkuReductionDto) {
        // sms_sku_ladder
        val skuLadderEntity = SkuLadderEntity()
        BeanUtils.copyProperties(skuReductionDto, skuLadderEntity)
        skuLadderEntity.addOther = skuReductionDto.countStatus
//        skuLadderEntity.price = ? 折后价
        skuLadderService.save(skuLadderEntity)

        // sms_sku_full_reduction
        val skuFullReductionEntity = SkuFullReductionEntity()
        BeanUtils.copyProperties(skuReductionDto, skuFullReductionEntity)
        skuLadderEntity.addOther = skuReductionDto.countStatus
        this.save(skuFullReductionEntity)

        // sms_member_price
        skuReductionDto.memberPrice?.mapNotNull {
            it ?: return@mapNotNull null
            val memberPriceEntity = MemberPriceEntity()
                .apply {
                    skuId = skuReductionDto.skuId
                    memberLevelId = it.id
                    memberPrice = it.price
                    memberLevelName = it.name
                    addOther = 1 //这里是设置的默认值
                }
            memberPriceEntity
        }?.let {
            memberPriceService.saveBatch(it)
        }

    }
}