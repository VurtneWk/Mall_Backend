package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.product.dao.CategoryDao
import com.vurtnewk.mall.product.entity.CategoryEntity
import com.vurtnewk.mall.product.service.CategoryService


@Service("categoryService")
class CategoryServiceImpl : ServiceImpl<CategoryDao, CategoryEntity>(), CategoryService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CategoryEntity>().getPage(params),
            QueryWrapper<CategoryEntity>()
        )
        return PageUtils(page)
    }

    override fun listWithTree(): List<CategoryEntity> {
        //1.查出所有分类
        // baseMapper 就是 CategoryDao
        val entities = baseMapper.selectList(null)
        //2.组装成父子树形结构
        //2.1 找出1级分类
        val list = entities.asSequence()
            .filter { it.parentCid == 0L }
            .map {
                it.children = getChildrenList(it, entities)
                it
            }
            .sortedBy { it.sort ?: 0 }
            .toList()

        return list
    }

    /**
     * 批量删除菜单
     */
    override fun removeMenuByIds(asList: List<Long>) {
        //TODO 检查当前删除的菜单 是否被其它地方引用过
        baseMapper.deleteByIds(asList)
    }

    /**
     * 递归查找从entities中查找root的子菜单
     */
    private fun getChildrenList(root: CategoryEntity, entities: List<CategoryEntity>): MutableList<CategoryEntity> {
        val entityList = entities.asSequence()
            .filter { it.parentCid == root.catId }
            .map {
                //递归查找子菜单
                it.children = getChildrenList(it, entities)
                it
            }
            //进行排序
            .sortedBy { it.sort ?: 0 }
            .toList()
        return entityList.toMutableList()
    }
}