package com.vurtnewk.mall.third_party.component

import com.vurtnewk.common.constants.ThirdPartyConstants
import com.vurtnewk.mall.third_party.utils.HttpUtils
import org.apache.http.HttpResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component

/**
 * 短信组件
 *
 * 为了安全和方便，属性都配置 nacos 配置中心的 third-party.properties 文档中
 *
 * @author   vurtnewk
 * @since    2025/1/20 16:38
 */
@Component
@RefreshScope
class SmsComponent {

    @Value("\${sms_host}")
    lateinit var host: String

    /**
     * 不能直接使用path，会取到本地变量..
     */
    @Value("\${sms_path}")
    lateinit var path: String

    @Value("\${sms_appcode}")
    lateinit var appcode: String

    @Value("\${sms_smsSignId}")
    lateinit var smsSignId: String

    @Value("\${sms_templateId}")
    lateinit var templateId: String


    /**
     * 短信生效时间
     * @param phone 接受验证码的手机
     * @param code 要发送的验证码
     */
    fun sendSmsCode(phone: String, code: String) {
        println("sendSmsCode start ")
        val method = "POST"
        val headers: MutableMap<String, String> = HashMap()
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers["Authorization"] = "APPCODE $appcode"
        val querys: MutableMap<String, String> = HashMap()
        querys["mobile"] = phone
        querys["param"] = "**code**:$code,**minute**:${ThirdPartyConstants.SMS_CODE_VALID_TIME}"
        //smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html
        querys["smsSignId"] = smsSignId
        querys["templateId"] = templateId
        val bodys: Map<String, String> = HashMap()

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            val response: HttpResponse = HttpUtils.doPost(host, path, method, headers, querys, bodys)
            println("sendSmsCode result = $response ")
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}