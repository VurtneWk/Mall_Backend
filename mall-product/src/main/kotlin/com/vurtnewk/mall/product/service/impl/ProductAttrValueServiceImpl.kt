package com.vurtnewk.mall.product.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.mall.product.dao.ProductAttrValueDao
import com.vurtnewk.mall.product.entity.ProductAttrValueEntity
import com.vurtnewk.mall.product.service.ProductAttrValueService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("productAttrValueService")
class ProductAttrValueServiceImpl : ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity>(), ProductAttrValueService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<ProductAttrValueEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    /**
     * 修改规格参数
     */
    @Transactional
    override fun updateSpuAttr(spuId: Long, entities: List<ProductAttrValueEntity>) {
        //可能新增或者取消一些数据，所以把原来的旧数据直接删了
        KtUpdateChainWrapper(ProductAttrValueEntity::class.java)
            .eq(ProductAttrValueEntity::spuId, spuId)
            .remove()

        val list = entities.map {
            it.spuId = spuId
            it
        }
        this.saveBatch(list)
    }

    /**
     * ## 获取spu规格
     */
    override fun baseAttrList(spuId: Long): List<ProductAttrValueEntity> {
        return KtQueryChainWrapper(ProductAttrValueEntity::class.java)
            .eq(ProductAttrValueEntity::spuId, spuId)
            .list()
    }
}