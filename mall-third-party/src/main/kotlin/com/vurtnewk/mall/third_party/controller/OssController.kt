package com.vurtnewk.mall.third_party.controller

import com.aliyun.oss.OSS
import com.aliyun.oss.common.utils.BinaryUtil
import com.aliyun.oss.model.MatchMode
import com.aliyun.oss.model.PolicyConditions
import com.vurtnewk.common.utils.R
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.*

/**
 * createTime:  2025/1/7 20:50
 * author:      vurtnewk
 * description: 文件上传 ， 返回验证，由前端直接上传到oss
 */
@RestController
class OssController {
    @Autowired
    var ossClient: OSS? = null

    @Value("\${endpoint}")
    private val endpoint: String? = null

    @Value("\${bucket}")
    private val bucket: String? = null

    @Value("\${accessKeyId}")
    private val accessId: String? = null

    @RequestMapping("/oss/policy")
    fun policy(): R {
        // host的格式为 bucketname.endpoint   用于拼接图片的访问地址
        val host = "https://$bucket.$endpoint"

        // 用户上传文件时指定的前缀。  此处定义每天建立一个文件夹
        val format = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var respMap: MutableMap<String?, String?>? = null
        try {
            val expireTime: Long = 30
            val expireEndTime = System.currentTimeMillis() + expireTime * 1000
            val expiration = Date(expireEndTime)
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            val policyConds = PolicyConditions()
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000)
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, format)
            val postPolicy = ossClient!!.generatePostPolicy(expiration, policyConds)
            val binaryData = postPolicy.toByteArray(charset("utf-8"))
            val encodedPolicy = BinaryUtil.toBase64String(binaryData)
            val postSignature = ossClient!!.calculatePostSignature(postPolicy)
            respMap = LinkedHashMap()
            respMap["accessid"] = accessId
            respMap["policy"] = encodedPolicy
            respMap["signature"] = postSignature
            respMap["dir"] = format
            respMap["host"] = host
            respMap["expire"] = (expireEndTime / 1000).toString()
        } catch (e: Exception) {
            // Assert.fail(e.getMessage());
            println(e.message)
        } finally {
            ossClient!!.shutdown()
        }
        return R.ok().put("data", respMap!!)
    }

}