package com.vurtnewk.mall.product

import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.product.service.BrandService
import com.vurtnewk.mall.product.service.CategoryService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    lateinit var brandService: BrandService


    @Autowired
    lateinit var categoryService: CategoryService

    @Test
    fun testFindCategoryPath(){
        val findCatelogPath = categoryService.findCatelogPath(225L)
        logInfo("完整路径：$findCatelogPath")
    }





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

//        val list = brandService.list(QueryWrapper<BrandEntity>().eq("brand_id", 1))
//        list.forEach {
//            println(it)
//        }
    }

}
