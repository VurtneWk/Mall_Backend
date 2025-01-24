package com.vurtnewk.mall.ware.service.impl

import com.alibaba.fastjson2.TypeReference
import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.common.utils.ext.toPage

import com.vurtnewk.mall.ware.dao.WareInfoDao
import com.vurtnewk.mall.ware.entity.WareInfoEntity
import com.vurtnewk.mall.ware.feign.MemberFeignService
import com.vurtnewk.mall.ware.service.WareInfoService
import com.vurtnewk.mall.ware.vo.FareVo
import com.vurtnewk.mall.ware.vo.MemberAddressVo
import java.math.BigDecimal


@Service("wareInfoService")
class WareInfoServiceImpl(
    private val memberFeignService: MemberFeignService,
) : ServiceImpl<WareInfoDao, WareInfoEntity>(), WareInfoService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val key = params["key"] as? String
        return KtQueryChainWrapper(WareInfoEntity::class.java)
            .and(!key.isNullOrBlank()) {
                it.eq(WareInfoEntity::id, key)
                    .or().like(WareInfoEntity::name, key)
                    .or().like(WareInfoEntity::address, key)
                    .or().like(WareInfoEntity::areacode, key)
            }
            .toPage(params)
            .pageUtils()
    }

    /**
     * 根据收货地址计算运费和返回收货地址
     */
    override fun getFare(addrId: Long): FareVo? {
        val r = memberFeignService.addrInfo(addrId)
        val memberAddressVo = r.getData("memberReceiveAddress", object : TypeReference<MemberAddressVo>() {})
        memberAddressVo?.phone?.let {
            val fareVo = FareVo()
            fareVo.fare = BigDecimal(it.substring((it.length - 1), it.length))
            fareVo.address = memberAddressVo
            return fareVo
        }
        return null
    }
}