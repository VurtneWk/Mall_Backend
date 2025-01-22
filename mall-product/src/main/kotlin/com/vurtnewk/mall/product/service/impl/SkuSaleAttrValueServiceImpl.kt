package com.vurtnewk.mall.product.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.mall.product.dao.SkuSaleAttrValueDao
import com.vurtnewk.mall.product.entity.SkuSaleAttrValueEntity
import com.vurtnewk.mall.product.service.SkuSaleAttrValueService
import com.vurtnewk.mall.product.vo.SkuItemSaleAttrVo
import org.springframework.stereotype.Service


@Service("skuSaleAttrValueService")
class SkuSaleAttrValueServiceImpl : ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity>() , SkuSaleAttrValueService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<SkuSaleAttrValueEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    override fun getSaleAttrsBySpuId(spuId: Long): List<SkuItemSaleAttrVo> {
        return this.baseMapper.getSaleAttrsBySpuId(spuId)
    }

    override fun getSkuSaleAttrValuesAsStringList(skuId: Long): List<String> {
        return this.baseMapper.getSkuSaleAttrValuesAsStringList(skuId)
    }
}