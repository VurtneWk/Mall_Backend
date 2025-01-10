package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.logDebug
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.mall.product.dao.AttrAttrgroupRelationDao

import com.vurtnewk.mall.product.dao.AttrDao
import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.entity.CategoryEntity
import com.vurtnewk.mall.product.service.AttrService
import com.vurtnewk.mall.product.vo.AttrRespVO
import com.vurtnewk.mall.product.vo.AttrVO
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional


@Service("attrService")
class AttrServiceImpl : ServiceImpl<AttrDao, AttrEntity>(), AttrService {

    @Autowired
    lateinit var mAttrAttrgroupRelationDao: AttrAttrgroupRelationDao

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<AttrEntity>().getPage(params),
            QueryWrapper<AttrEntity>()
        )
        return PageUtils(page)
    }

    @Transactional
    override fun saveAttrVO(attr: AttrVO) {
        //1.保存自己到属性表
        val attrEntity = AttrEntity()
        BeanUtils.copyProperties(attr, attrEntity)
        this.save(attrEntity)
        //2.保存所属分组到关联表
        val entity = AttrAttrgroupRelationEntity()
        entity.attrGroupId = attr.attrGroupId
        entity.attrId = attrEntity.attrId
        mAttrAttrgroupRelationDao.insert(entity)
    }

    //所属分类、所属分组没有
    override fun queryBaseAttrPage(params: Map<String, Any>, catelogId: Long): PageUtils {
        val key = params["key"] as? String?
        val page = KtQueryChainWrapper(AttrEntity::class.java)
            .eq(catelogId != 0L, AttrEntity::catelogId, catelogId)
            .and(!key.isNullOrBlank()) { it.eq(AttrEntity::attrId, key).or().like(AttrEntity::attrName, key) }
            .page(Query<AttrEntity>().getPage(params))

        val pageUtils = page.pageUtils()
        //给查询出来的数据，少了分类和分组的名字，进行再次查询加上
        /**
         * 这里是挨个遍历，然后去查询对应的groupName，catelogName
         * 至于是遍历查询还是链表查询？
         *  - 数据量小，逻辑清晰优先
         *  - 数据量大，性能优先
         *
         * 索引优化、批量查询、缓存机制 (redis)、NoSQL 解决方案（elasticSearch）
         */
        val list = page.records.map { attrEntity ->
            val attrRespVO = AttrRespVO()
            BeanUtils.copyProperties(attrEntity, attrRespVO)
            //1. 设置分类和分组的名字
            //查询关系表
            val attrAttrgroupRelationEntity = KtQueryChainWrapper(AttrAttrgroupRelationEntity::class.java)
                .eq(AttrAttrgroupRelationEntity::attrId, attrEntity.attrId)
                .one()
            if (attrAttrgroupRelationEntity != null) {
                //现在根据关系表查询对应的分组名
                KtQueryChainWrapper(AttrGroupEntity::class.java)
                    .eq(AttrGroupEntity::attrGroupId, attrAttrgroupRelationEntity.attrGroupId)
                    .one()
                    .let { attrGroupEntity ->
                        attrRespVO.groupName = attrGroupEntity.attrGroupName
                    }
            }
            //
            KtQueryChainWrapper(CategoryEntity::class.java)
                .eq(CategoryEntity::catId, attrEntity.catelogId)
                .one()
                ?.let {
                    attrRespVO.catelogName = it.name
                }
            attrRespVO
        }.toList()
        pageUtils.list = list
        return pageUtils
    }
}