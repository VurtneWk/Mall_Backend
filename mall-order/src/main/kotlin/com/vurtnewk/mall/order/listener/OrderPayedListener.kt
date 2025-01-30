package com.vurtnewk.mall.order.listener

import com.alipay.api.AlipayConfig
import com.alipay.api.internal.util.AlipaySignature
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.order.config.AlipayTemplate
import com.vurtnewk.mall.order.service.OrderService
import com.vurtnewk.mall.order.vo.PayAsyncVo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 支付宝成功的通知
 * @author   vurtnewk
 * @since    2025/1/30 18:56
 */
@RestController
class OrderPayedListener(
    private val orderService: OrderService,
    private val alipayTemplate: AlipayTemplate,
) {


    @PostMapping("/payed/notify")
    fun handleAliPayed(payAsyncVo: PayAsyncVo, httpServletRequest: HttpServletRequest): String {
        logInfo("=====handleAliPayed=====")
        // 验签！！！！！！！！！！！！！！！！！！！！！！！
        //获取支付宝POST过来反馈信息
        val params = HashMap<String, String>()
        val requestParams = httpServletRequest.parameterMap
        for ((name, values) in requestParams) {
            val valueStr = values.joinToString(",") // 将数组拼接成字符串
            // 乱码解决，这段代码在出现乱码时使用
//            val decodedValueStr = String(valueStr.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
            params[name] = valueStr
        }
        // 调用 SDK 验证签名
        val checkResult = AlipaySignature.rsaCheckV1(params, alipayTemplate.alipayPublicKey, alipayTemplate.charset, alipayTemplate.signType)
        if(!checkResult) return "error"
        return orderService.handlePayResult(payAsyncVo)
    }
}