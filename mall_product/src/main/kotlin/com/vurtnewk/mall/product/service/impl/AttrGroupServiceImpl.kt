package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.mall.product.dao.AttrAttrgroupRelationDao
import com.vurtnewk.mall.product.dao.AttrDao

import com.vurtnewk.mall.product.dao.AttrGroupDao
import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.service.AttrGroupService
import com.vurtnewk.mall.product.vo.AttrGroupRelationVO
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired


@Service("attrGroupService")
class AttrGroupServiceImpl : ServiceImpl<AttrGroupDao, AttrGroupEntity>(), AttrGroupService {

    @Autowired
    lateinit var mAttrDao: AttrDao

    @Autowired
    lateinit var mAttrAttrgroupRelationDao: AttrAttrgroupRelationDao


    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<AttrGroupEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    override fun queryPage(params: Map<String, Any>, catelogId: Long): PageUtils {
        val iPage = Query<AttrGroupEntity>().getPage(params)
        val key = params["key"] as String?
        //select * from pms_attr_group where catelog_id = ? and (attr_group_id = key or attr_group_name like key)
        val ktQueryChainWrapper = KtQueryChainWrapper(AttrGroupEntity::class.java)
            .and(!key.isNullOrEmpty()) {
                it.eq(AttrGroupEntity::attrGroupId, key)
                    .or().like(AttrGroupEntity::attrGroupName, key)
            }
        if (catelogId != 0L) {
            ktQueryChainWrapper.eq(AttrGroupEntity::catelogId, catelogId)
        }
        return ktQueryChainWrapper.page(iPage).pageUtils()
    }

    /**
     * 批量删除关联关系
     * 需要的sql
     * DELETE FROM pms_attr_attrgroup_relation WHERE ((attr_id = ? AND attr_group_id = ?) OR (attr_id = ? AND attr_group_id = ?))
     * DELETE FROM pms_attr_attrgroup_relation WHERE (attr_id =? AND attr_group_id=?)
     */
    override fun deleteRelation(attrGroupRelationVOList: List<AttrGroupRelationVO>) {
        //方式一: 通过ktUpdateChainWrapper来生成sql
//        val ktUpdateChainWrapper = KtUpdateChainWrapper(AttrAttrgroupRelationEntity::class.java)
//        attrGroupRelationVOList.forEachIndexed { index, attrGroupRelationVO ->
//            if (index == 0) {
//                ktUpdateChainWrapper.and {
//                    it.eq(AttrAttrgroupRelationEntity::attrId, attrGroupRelationVO.attrId)
//                        .eq(AttrAttrgroupRelationEntity::attrGroupId, attrGroupRelationVO.attrGroupId)
//                }
//            } else {
//                ktUpdateChainWrapper.or{
//                    it.eq(AttrAttrgroupRelationEntity::attrId, attrGroupRelationVO.attrId)
//                        .eq(AttrAttrgroupRelationEntity::attrGroupId, attrGroupRelationVO.attrGroupId)
//                }
//            }
//        }
//        ktUpdateChainWrapper.remove()
        //方式二： 通过SQL语句
        //为了 Dao 只操作 Entity (PO) ，而不直接操作 VO
        val list = attrGroupRelationVOList.map {
            val attrAttrgroupRelationEntity = AttrAttrgroupRelationEntity()
            BeanUtils.copyProperties(it,attrAttrgroupRelationEntity)
            attrAttrgroupRelationEntity
        }
        mAttrAttrgroupRelationDao.deleteRelation(list)
    }

    override fun getAttrGrouprelation(attrgroupId: Long): List<AttrEntity> {
        //通过中间表 查询指定attrGroupId的所有attrId
        val idList = KtQueryChainWrapper(AttrAttrgroupRelationEntity::class.java)
            .eq(AttrAttrgroupRelationEntity::attrGroupId, attrgroupId)
            .list()
            ?.mapNotNull {
                it.attrId
            } ?: emptyList()

        //如果没有查询到 直接返回空列表
        if (idList.isEmpty()) return emptyList()

        return KtQueryChainWrapper(AttrEntity::class.java)
            .`in`(AttrEntity::attrId, idList)
            .list()
    }
}