package com.vurtnewk.mall.ware.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.constants.WareConstants
import com.vurtnewk.common.excetion.CustomException
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.ware.dao.PurchaseDao
import com.vurtnewk.mall.ware.entity.PurchaseDetailEntity
import com.vurtnewk.mall.ware.entity.PurchaseEntity
import com.vurtnewk.mall.ware.service.PurchaseDetailService
import com.vurtnewk.mall.ware.service.PurchaseService
import com.vurtnewk.mall.ware.service.WareSkuService
import com.vurtnewk.mall.ware.vo.MergePurchaseOrderVo
import com.vurtnewk.mall.ware.vo.PurchaseOrderDoneVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service("purchaseService")
class PurchaseServiceImpl(
    private val purchaseDetailService: PurchaseDetailService,
    private val wareSkuService: WareSkuService,
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
        KtUpdateChainWrapper(PurchaseEntity::class.java)
            .set(PurchaseEntity::updateTime, Date())
            .eq(PurchaseEntity::id, purchaseOrderVo.purchaseId)
            .update()
    }

    /**
     * ## 领取采购单
     * ```json
     * [1,2,3,4]
     * ```
     * ## 注意点
     *
     * 1. 确认当前采购单是新建或者已分配状态
     * 2. 改变采购单的状态
     * 3. 改变采购项的状态
     */
    @Transactional
    override fun receivedPurchaseOrder(purchaseIds: List<Long>) {
        if (purchaseIds.isEmpty()) return

        //1、确认当前采购单是新建或者已分配状态
        // 查出参数里，状态满足的采购单
        val purchaseEntities = KtQueryChainWrapper(PurchaseEntity::class.java)
            .`in`(PurchaseEntity::id, purchaseIds)
            .and {
                it.eq(PurchaseEntity::status, WareConstants.PurchaseStatusEnum.CREATED.code)
                    .or().eq(PurchaseEntity::status, WareConstants.PurchaseStatusEnum.ASSIGNED.code)
            }
            .list()
            .map {
                it.status = WareConstants.PurchaseStatusEnum.RECEIVE.code
                it.updateTime = Date()
                it
            }
        //2、改变采购单的状态
        if (purchaseEntities.isEmpty()) {
            logInfo("没有符合条件的采购需求")
            throw CustomException("没有符合条件的采购需求")
        }
        this.updateBatchById(purchaseEntities)

        //3、改变采购项的状态
        val purchaseDetailEntities = KtQueryChainWrapper(PurchaseDetailEntity::class.java)
            .`in`(PurchaseDetailEntity::purchaseId, purchaseEntities.map { it.id })
            .list()
            .map {
                it.status = WareConstants.PurchaseDetailStatusEnum.BUYING.code
                it
            }
        if (purchaseDetailEntities.isEmpty()) {
            logInfo("这个采购单下，没有采购需求")
            throw CustomException("这个采购单下，没有采购需求")
        }
        purchaseDetailService.updateBatchById(purchaseDetailEntities)
    }

    /**
     * 1. 改变采购单状态
     * 2. 改变采购项的状态
     * 3. 将成功采购的进行入库
     */
    @Transactional
    override fun donePurchaseOrder(purchaseOrderDoneVo: PurchaseOrderDoneVo) {

        var isSuccess = true
        val updates = mutableListOf<PurchaseDetailEntity>()
        purchaseOrderDoneVo.items.forEach { item ->
            val purchaseDetailEntity = PurchaseDetailEntity()
            purchaseDetailEntity.status = item.status
            purchaseDetailEntity.id = item.itemId
            if (item.status == WareConstants.PurchaseDetailStatusEnum.ERROR.code) {
                isSuccess = false
            } else {
                //将成功采购的进行入库
                val detailEntity = purchaseDetailService.getById(item.itemId)
                wareSkuService.addStock(detailEntity.skuId!!, detailEntity.wareId!!, detailEntity.skuNum!!)
            }
            updates.add(purchaseDetailEntity)
        }
        //改变采购单状态
        purchaseDetailService.updateBatchById(updates)

        //更新订单
        KtUpdateChainWrapper(PurchaseEntity::class.java)
            .set(PurchaseEntity::status, if (isSuccess) WareConstants.PurchaseStatusEnum.FINISH.code else WareConstants.PurchaseStatusEnum.ERROR.code)
            .eq(PurchaseEntity::id, purchaseOrderDoneVo.id)
            .update()
    }
}