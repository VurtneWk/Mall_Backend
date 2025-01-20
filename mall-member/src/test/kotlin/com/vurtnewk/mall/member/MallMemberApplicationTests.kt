package com.vurtnewk.mall.member

import com.vurtnewk.mall.member.feign.CouponFeignService
import org.apache.commons.codec.digest.DigestUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MallMemberApplicationTests {


    @Autowired
    lateinit var c: CouponFeignService

    @Test
    fun contextLoads() {
        println(c)
        DigestUtils.md5("123456")

    }

}
