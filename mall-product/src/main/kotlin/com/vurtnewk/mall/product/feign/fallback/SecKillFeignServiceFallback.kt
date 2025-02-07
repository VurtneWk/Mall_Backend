package com.vurtnewk.mall.product.feign.fallback

import com.vurtnewk.common.excetion.BizCodeEnum
import com.vurtnewk.common.utils.R
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.product.feign.SecKillFeignService
import org.springframework.stereotype.Component

/**
 * 远程调用的失败回调
 * @author   vurtnewk
 * @since    2025/2/7 17:32
 */
@Component
class SecKillFeignServiceFallback : SecKillFeignService {

    override fun getSkuSecKillInfo(skuId: Long): R {
       logInfo("SecKillFeignServiceFallback => 熔断方法调用")
       return R.error(BizCodeEnum.TOO_MANY_REQUEST_EXCEPTION)
    }
}