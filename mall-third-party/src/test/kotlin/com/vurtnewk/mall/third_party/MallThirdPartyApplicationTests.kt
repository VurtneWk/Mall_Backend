package com.vurtnewk.mall.third_party

import com.aliyun.oss.OSSClient
import com.vurtnewk.mall.third_party.component.SmsComponent
import com.vurtnewk.mall.third_party.utils.HttpUtils
import org.apache.http.HttpResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.FileInputStream
import java.io.FileNotFoundException


@SpringBootTest
class MallThirdPartyApplicationTests {

	@Autowired
	lateinit var ossClient: OSSClient
	@Autowired
	lateinit var mSmsComponent: SmsComponent

	@Test
	fun testSendCode2(){
		mSmsComponent.sendSmsCode("18301532281","321321")
	}


	@Test
	@Throws(FileNotFoundException::class)
	fun testUpload() {
		val fileInputStream = FileInputStream("/Users/vurtnewk/Documents/data/video/后端-谷粒商城项目/1.分布式基础（全栈）/docs/pics/2b1837c6c50add30.jpg")
		ossClient.putObject("mall-vurtnewk", "hah.jpg", fileInputStream)
		ossClient.shutdown()
		println("上传完成")
	}

	@Test
	fun contextLoads() {
	}

}
