package com.vurtnewk.mall.order.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 线程配置
 * @author   vurtnewk
 * @since    2025/1/20 10:32
 */
@ConfigurationProperties(prefix = "mall.thread")
@Component
class ThreadPoolConfigProperties {

    var coreSize: Int = 20
    var maxSize : Int = 200
    var keepAliveTime : Long = 60

}