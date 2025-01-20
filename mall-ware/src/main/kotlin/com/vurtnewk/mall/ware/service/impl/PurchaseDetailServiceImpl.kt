package com.vurtnewk.mall.ware.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage
import com.vurtnewk.mall.ware.dao.PurchaseDetailDao
import com.vurtnewk.mall.ware.entity.PurchaseDetailEntity
import com.vurtnewk.mall.ware.service.PurchaseDetailService
import org.springframework.stereotype.Service


@Service("purchaseDetailService")
class PurchaseDetailServiceImpl : ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity>(), PurchaseDetailService {

    /**
     * ## 查询采购需求
     * ### 参数
     * ```json
     * {
     *    page: 1,//当前页码
     *    limit: 10,//每页记录数
     *    key: '华为',//检索关键字
     *    status: 0,//状态 [0新建，1已分配，2正在采购，3已完成，4采购失败]
     *    wareId: 1,//仓库id
     * }
     * ```
     */
    override fun queryPage(params: Map<String, Any>): PageUtils {
        val key = params["key"] as? String
        val status = params["status"] as? String
        val wareId = params["wareId"] as? String

        return KtQueryChainWrapper(PurchaseDetailEntity::class.java)
            .and(!key.isNullOrBlank()) {
                it.eq(PurchaseDetailEntity::purchaseId, key).or().eq(PurchaseDetailEntity::skuId, key)
            }
            .eq(!status.isNullOrBlank(), PurchaseDetailEntity::status, status)
            .eq(!wareId.isNullOrBlank(), PurchaseDetailEntity::wareId, wareId)
            .toPage(params).pageUtils()
    }
}