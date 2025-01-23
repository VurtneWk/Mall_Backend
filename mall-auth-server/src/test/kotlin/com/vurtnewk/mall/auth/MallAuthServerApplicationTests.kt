package com.vurtnewk.mall.auth

import jakarta.servlet.http.HttpSession
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MallAuthServerApplicationTests {

	@Autowired
	lateinit var httpSession: HttpSession


	@Test
	fun contextLoads() {
		println(httpSession.maxInactiveInterval)
	}

}
