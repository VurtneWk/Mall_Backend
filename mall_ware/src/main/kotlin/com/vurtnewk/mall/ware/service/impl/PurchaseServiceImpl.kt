package com.vurtnewk.mall.ware.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.constants.WareConstants
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.ware.dao.PurchaseDao
import com.vurtnewk.mall.ware.entity.PurchaseDetailEntity
import com.vurtnewk.mall.ware.entity.PurchaseEntity
import com.vurtnewk.mall.ware.service.PurchaseDetailService
import com.vurtnewk.mall.ware.service.PurchaseService
import com.vurtnewk.mall.ware.vo.MergePurchaseOrderVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service("purchaseService")
class PurchaseServiceImpl(
    private val purchaseDetailService: PurchaseDetailService
) : ServiceImpl<PurchaseDao, PurchaseEntity>(), PurchaseService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<PurchaseEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    override fun queryUnreceivedList(params: Map<String, Any>): PageUtils {
        return KtQueryChainWrapper(PurchaseEntity::class.java)
            .eq(PurchaseEntity::status, WareConstants.PurchaseStatusEnum.CREATED.code)
            .or()
            .eq(PurchaseEntity::status, WareConstants.PurchaseStatusEnum.ASSIGNED.code)
            .toPage(params).pageUtils()
    }

    /**
     * ## 合并采购需求
     */
    @Transactional
    override fun mergePurchaseOrder(purchaseOrderVo: MergePurchaseOrderVo) {
        if (purchaseOrderVo.items.isNullOrEmpty()) {
            return
        }
        // 确认采购单状态是0或1才可以
        val errorList = KtQueryChainWrapper(PurchaseDetailEntity::class.java)
            .`in`(PurchaseDetailEntity::id, purchaseOrderVo.items)
            .ne(PurchaseDetailEntity::status, WareConstants.PurchaseDetailStatusEnum.CREATED.code)
            .ne(PurchaseDetailEntity::status, WareConstants.PurchaseDetailStatusEnum.ASSIGNED.code)
            .list()
        if (!errorList.isNullOrEmpty()) {
            throw Exception("采购单状态异常")
        }

        // 如果 purchaseId 是 null ，则新建一个采购单
        if (purchaseOrderVo.purchaseId == null) {
            val purchaseEntity = PurchaseEntity()
                .apply {
                    createTime = Date()
                    updateTime = Date()
                    status = WareConstants.PurchaseStatusEnum.CREATED.code
                }
            this.save(purchaseEntity)
            purchaseOrderVo.purchaseId = purchaseEntity.id
        }

        purchaseOrderVo.items.map {
            val purchaseDetailEntity = PurchaseDetailEntity()
                .apply {
                    purchaseId = purchaseOrderVo.purchaseId
                    id = it
                    status = WareConstants.PurchaseDetailStatusEnum.ASSIGNED.code
                }
            purchaseDetailEntity
        }.apply {
            purchaseDetailService.updateBatchById(this)
        }
        //更新完成之后，采购单更新
        val purchaseEntity = PurchaseEntity()
        purchaseEntity.updateTime = Date()
        KtUpdateChainWrapper(PurchaseEntity::class.java)
            .eq(PurchaseEntity::id, purchaseOrderVo.purchaseId)
            .update(purchaseEntity)
    }
}