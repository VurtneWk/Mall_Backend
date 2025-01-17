package com.vurtnewk.mall.product.service.impl

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference
import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.product.dao.CategoryBrandRelationDao

import com.vurtnewk.mall.product.dao.CategoryDao
import com.vurtnewk.mall.product.entity.CategoryEntity
import com.vurtnewk.mall.product.service.CategoryService
import com.vurtnewk.mall.product.vo.Catalog2Vo
import org.redisson.api.RedissonClient
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.concurrent.TimeUnit


@Service("categoryService")
class CategoryServiceImpl(
    private val mStringRedisTemplate: StringRedisTemplate,
    private val mCategoryBrandRelationDao: CategoryBrandRelationDao,
    private val mRedissonClient: RedissonClient,
) : ServiceImpl<CategoryDao, CategoryEntity>(), CategoryService {

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

//    @CacheEvict(value = ["category"], key = "'level1Categorys'")
    /**
     * 同时删除多个缓存
     * 方式1. 使用 Caching
     * 方式2. CacheEvict 的 allEntries 删除所有分区
     */
//    @Caching(evict = [
//        CacheEvict(value = ["category"], key = "'level1Categorys'"),
//        CacheEvict(value = ["category"], key = "'getCatalogJson'")
//    ])
    @CacheEvict(value = ["category"], allEntries = true)
    @Transactional
    override fun updateCascade(category: CategoryEntity) {
        this.updateById(category)
        //修改关联的分类名
        if (!category.name.isNullOrBlank()) {
            mCategoryBrandRelationDao.updateCategoryName(category.catId!!, category.name!!)
        }
    }

    /**
     * 使用注解来加缓存
     */
    @Cacheable(value = ["category"], key = "#root.methodName")
    override fun getCatalogJson(): Map<String, List<Catalog2Vo>> {
        logInfo("开始查询数据库..")
        //优化： 只查一次数据库
        val allCategoryEntities = KtQueryChainWrapper(CategoryEntity::class.java).list()
        val topLevelCategoryList = this.getCategoryListByParentCId(allCategoryEntities, 0L)
        val catalogJsonFromDb = topLevelCategoryList.associate { categoryEntity ->
            //根据一级ID 查出所有的二级
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
        return catalogJsonFromDb
    }

    /**
     * ## 缓存问题解决
     * 1. 空结果缓存 --- 解决缓存穿透: 大量并发访问一个不存在的数据
     * 2. 设置过期时间（加随机值）--- 解决缓存雪崩: 大量并发访问时缓存同时过期
     * 3. 加锁 --- 解决缓存击穿: 一个热点数据过期时，大量并发访问
     */
    fun getCatalogJson02(): Map<String, List<Catalog2Vo>> {
        val opsForValue = mStringRedisTemplate.opsForValue()
        val catalogJson = opsForValue.get("catalogJson")
        return if (catalogJson.isNullOrEmpty()) {
            this.getCatalogJsonFromDbWithRedissonLock()
        } else {
            JSON.parseObject(catalogJson, object : TypeReference<Map<String, List<Catalog2Vo>>>() {})
        }
    }


    /**
     * ## 使用的本地锁
     */
    @Synchronized //默认会使用this对象所谓锁，Spring中默认Bean对象是单例的
    fun getCatalogJsonFromDbWithLocalLock(): Map<String, List<Catalog2Vo>> {
        return getCatalogJsonFromDb()
    }

    /**
     * ## 使用的Redisson锁
     * ---------------
     * 缓存里面的数据如何和数据库保持一致
     * 缓存数据一致性
     * 1、双写模式 ： 更新的同时修改缓存中的数据
     * 2、失效模式 ： 更新的同时删除缓存中的数据
     */
    fun getCatalogJsonFromDbWithRedissonLock(): Map<String, List<Catalog2Vo>> {
        //1. 锁的名字。锁的粒度，越细越快
        //锁的粒度：具体缓存的某个数据， 11-号商品 product-11-lock，避免直接起名为：product-lock
        val lock = mRedissonClient.getLock("catalogJson-lock")
        lock.lock()
        var catalogJsonFromDb: Map<String, List<Catalog2Vo>> = emptyMap()
        kotlin.runCatching {
            catalogJsonFromDb = getCatalogJsonFromDb()
        }
        lock.unlock()
        return catalogJsonFromDb
    }

    /**
     * ## 使用的Redis锁
     */
    fun getCatalogJsonFromDbWithRedisLock(): Map<String, List<Catalog2Vo>> {
        val uuid = UUID.randomUUID().toString()
        //设置 Redis 锁，
        val lock = mStringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS) ?: false
        return if (lock) {
            //锁成功了 才操作数据库
            val script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end"
            val execute = mStringRedisTemplate.execute(DefaultRedisScript(script, Long::class.java), listOf("lock"), uuid)
            getCatalogJsonFromDb()
        } else {
            //锁失败了进行重试
            Thread.sleep(2000)
            getCatalogJsonFromDbWithRedisLock()
        }
    }

    private fun getCatalogJsonFromDb(): Map<String, List<Catalog2Vo>> {
        //得到锁之后，应该再去缓存中确认一次 double check
        val opsForValue = mStringRedisTemplate.opsForValue()
        val catalogJson = opsForValue.get("catalogJson")
        //如果缓存中有数据 直接返回
        if (!catalogJson.isNullOrEmpty()) {
            return JSON.parseObject(catalogJson, object : TypeReference<Map<String, List<Catalog2Vo>>>() {})
        }
        logInfo("开始查询数据库..")
        //优化： 只查一次数据库
        val allCategoryEntities = KtQueryChainWrapper(CategoryEntity::class.java).list()
        val topLevelCategoryList = this.getCategoryListByParentCId(allCategoryEntities, 0L)
        val catalogJsonFromDb = topLevelCategoryList.associate { categoryEntity ->
            //根据一级ID 查出所有的二级
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
        //存入数据应该放到锁中，避免还没来得及放入缓存，就被下一个线程判断 是否有缓存时 获取的是空的
        opsForValue.set("catalogJson", JSON.toJSONString(catalogJsonFromDb), 1, TimeUnit.DAYS)
        return catalogJsonFromDb
    }

    private fun getCategoryListByParentCId(entities: List<CategoryEntity>, parentCid: Long): List<CategoryEntity> {
        return entities.filter { it.parentCid == parentCid }
    }

    /**
     * ## 缓存解释
     * -  @Cacheable： 代表当前方法的结果需要缓存，如果缓存中有，方法不用调用。如果缓存中没有，会调用方法，最后将方法的结果放入缓存
     * -  每一个需要缓存的数据我们都来制定要放到那个名字的缓存。[缓存分区 (推荐按照业务分区)]
     */
    @Cacheable(value = ["category"], key = "'level1Categorys'")
    override fun getTopLevelCategoryList(): List<CategoryEntity> {
        logInfo("查询一级分类")
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