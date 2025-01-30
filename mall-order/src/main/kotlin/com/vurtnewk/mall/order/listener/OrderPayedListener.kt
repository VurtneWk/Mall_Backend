package com.vurtnewk.mall.order.listener

import com.vurtnewk.common.utils.ext.logInfo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 支付宝成功的通知
 * @author   vurtnewk
 * @since    2025/1/30 18:56
 */
@RestController
class OrderPayedListener {


    @PostMapping("/payed/notify")
    fun handleAliPayed(httpServletRequest: HttpServletRequest): String {
        logInfo("=====handleAliPayed=====")
        return "success"
    }
}