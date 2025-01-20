package com.vurtnewk.mall.coupon

import com.vurtnewk.mall.coupon.controller.CouponController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MallCouponApplicationTests {

    @Autowired
    lateinit var mCouponController: CouponController


    @Test
    fun contextLoads() {
        val test = mCouponController.test()
        println(test)
    }

}
