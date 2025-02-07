package com.vurtnewk.mall.gateway.config

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager
import com.alibaba.fastjson2.JSON
import com.vurtnewk.common.excetion.BizCodeEnum
import com.vurtnewk.common.utils.R
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 *
 * @author   vurtnewk
 * @since    2025/2/7 18:45
 */
@Configuration
class GatewaySentinelConfig {

    init {
        GatewayCallbackManager.setBlockHandler { _, _ ->
            val errorJson: String = JSON.toJSONString(R.error(BizCodeEnum.TOO_MANY_REQUEST_EXCEPTION))
            ServerResponse.ok().body(Mono.just(errorJson), String::class.java)
        }
    }
}