package com.vurtnewk.mall.product

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.vurtnewk.mall.product.entity.BrandEntity
import com.vurtnewk.mall.product.service.BrandService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    lateinit var brandService: BrandService

    @Test
    fun contextLoads() {
//        val entity = BrandEntity()
//新增
//        entity.name = "华为"
//        brandService.save(entity)
//        println("保存成功")

        //修改
//        entity.brandId = 1
//        entity.descript = "华为华为"
//        brandService.updateById(entity)

        //查询

        val list = brandService.list(QueryWrapper<BrandEntity>().eq("brand_id", 1))
        list.forEach {
            println(it)
        }
    }

}
