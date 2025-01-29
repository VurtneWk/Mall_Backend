package com.vurtnewk.mall.order.config

import com.alipay.api.AlipayApiException
import com.alipay.api.AlipayClient
import com.alipay.api.DefaultAlipayClient
import com.alipay.api.request.AlipayTradePagePayRequest
import com.vurtnewk.mall.order.vo.PayVo
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


/**
 *
 * @author   vurtnewk
 * @since    2025/1/30 00:31
 */
@ConfigurationProperties(prefix = "alipay")
@Component
class AlipayTemplate {

    //在支付宝创建的应用的id
    var appId = ""

    // 商户私钥，您的PKCS8格式RSA2私钥
    var merchantPrivateKey = ""

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    var alipayPublicKey = ""

    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    var notifyUrl: String? = null

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    var returnUrl: String? = null

    // 签名方式
    var signType = ""

    // 字符编码格式
    var charset = ""

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    var gatewayUrl = ""

    @Throws(AlipayApiException::class)
    fun pay(vo: PayVo): String {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        val alipayClient: AlipayClient = DefaultAlipayClient(
            gatewayUrl,
            appId, merchantPrivateKey, "json",
            charset, alipayPublicKey, signType
        )

        //2、创建一个支付请求 //设置请求参数
        val alipayRequest = AlipayTradePagePayRequest()
        alipayRequest.returnUrl = returnUrl
        alipayRequest.notifyUrl = notifyUrl

        //商户订单号，商户网站订单系统中唯一订单号，必填
//        val out_trade_no = vo.outTradeNo
        //付款金额，必填
//        val total_amount = vo.totalAmount
        //订单名称，必填
        val subject = vo.subject
        //商品描述，可空
        val body = vo.body
        alipayRequest.bizContent = ("{\"out_trade_no\":\"" + vo.outTradeNo + "\","
                + "\"total_amount\":\"" + vo.totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}")
        val result = alipayClient.pageExecute(alipayRequest).body

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        println("支付宝的响应：$result")
        return result
    }

}