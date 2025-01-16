package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.mall.product.dao.CategoryBrandRelationDao

import com.vurtnewk.mall.product.dao.CategoryDao
import com.vurtnewk.mall.product.entity.CategoryEntity
import com.vurtnewk.mall.product.service.CategoryService
import com.vurtnewk.mall.product.vo.Catalog2Vo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional


@Service("categoryService")
class CategoryServiceImpl : ServiceImpl<CategoryDao, CategoryEntity>(), CategoryService {

    @Autowired
    private lateinit var mCategoryBrandRelationDao: CategoryBrandRelationDao

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<CategoryEntity>().getPage(params),
            QueryWrapper()
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

    override fun findCatelogPath(catelogId: Long): List<Long> {
        return findParentPath(catelogId, mutableListOf()).reversed()
    }

    @Transactional
    override fun updateCascade(category: CategoryEntity) {
        this.updateById(category)
        //修改关联的分类名
        if (!category.name.isNullOrBlank()) {
            mCategoryBrandRelationDao.updateCategoryName(category.catId!!, category.name!!)
        }
    }


    override fun getCatalogJson(): Map<String, List<Catalog2Vo>> {
        //优化： 只查一次数据库

        val allCategoryEntities = KtQueryChainWrapper(CategoryEntity::class.java).list()


//        val topLevelCategoryList = this.getTopLevelCategoryList()
        val topLevelCategoryList = this.getCategoryListByParentCId(allCategoryEntities, 0L)
        return topLevelCategoryList.associate { categoryEntity ->
            //根据一级ID 查出所有的二级
//            val categoryEntities = KtQueryChainWrapper(CategoryEntity::class.java)
//                .eq(CategoryEntity::parentCid, categoryEntity.catId)
//                .list() //查出二级分类
            val categoryEntities = this.getCategoryListByParentCId(allCategoryEntities, categoryEntity.parentCid!!)
                .map { category2Entity ->
                    //组装2级数据
                    val catalog2Vo = Catalog2Vo()
                    catalog2Vo.catalog1Id = categoryEntity.catId.toString()
                    catalog2Vo.id = category2Entity.catId?.toString().orEmpty()
                    catalog2Vo.name = category2Entity.name.orEmpty()
                    // catalog2Vo.catalog3List =
                    //根据2级ID 查询3级数据
                    catalog2Vo.catalog3List = this.getCategoryListByParentCId(allCategoryEntities, category2Entity.parentCid!!)
                        .map { category3Entity ->
                            //组装3级数据
                            Catalog2Vo.Catalog3Vo(
                                category2Entity.catId.toString(),
                                category3Entity.catId.toString(),
                                category3Entity.name.toString()
                            )
                        }
                    catalog2Vo
                }
            //组装成map
            Pair(categoryEntity.catId.toString(), categoryEntities)
        }

    }

    private fun getCategoryListByParentCId(entities: List<CategoryEntity>, parentCid: Long): List<CategoryEntity> {
        return entities.filter { it.parentCid == parentCid }
    }

    override fun getTopLevelCategoryList(): List<CategoryEntity> {
        return KtQueryChainWrapper(CategoryEntity::class.java)
            .eq(CategoryEntity::parentCid, 0)
            .list()
    }

    /**
     * 递归查找指定catelogId的父ID
     */
    private fun findParentPath(catelogId: Long, paths: MutableList<Long>): MutableList<Long> {
        paths.add(catelogId)
        val categoryEntity = this.getById(catelogId)
        if (categoryEntity.parentCid != null && categoryEntity.parentCid != 0L) {
            findParentPath(categoryEntity.parentCid!!, paths)
        }
        return paths
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