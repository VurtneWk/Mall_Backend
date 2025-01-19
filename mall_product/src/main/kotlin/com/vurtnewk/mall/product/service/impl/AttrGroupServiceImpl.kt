package com.vurtnewk.mall.product.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.constants.ProductConstants
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.pageUtils
import com.vurtnewk.mall.product.dao.AttrAttrgroupRelationDao
import com.vurtnewk.mall.product.dao.AttrGroupDao
import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.entity.AttrGroupEntity
import com.vurtnewk.mall.product.service.AttrGroupService
import com.vurtnewk.mall.product.vo.AttrGroupRelationVO
import com.vurtnewk.mall.product.vo.AttrGroupWithAttrsVO
import com.vurtnewk.mall.product.vo.SpuItemAttrGroupVo
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service("attrGroupService")
class AttrGroupServiceImpl : ServiceImpl<AttrGroupDao, AttrGroupEntity>(), AttrGroupService {

//    @Autowired
//    lateinit var mAttrDao: AttrDao

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
            BeanUtils.copyProperties(it, attrAttrgroupRelationEntity)
            attrAttrgroupRelationEntity
        }
        mAttrAttrgroupRelationDao.deleteRelation(list)
    }

    /**
     * 指定属性 attrGroupId 下没有被关系的属性
     * 1. pms_attr_group 表和 pms_attr 表、 catelog_id 要一致
     * 2. 且 不在 pms_attr_attrgroup_relation 表中 （也就是没有和其它分组进行关联）
     */
    override fun attrNotRelation(params: Map<String, Any>, attrGroupId: Long): PageUtils {
        // 根据 attrGroupId 在 pms_attr_group 里获取 catelog_id
        // 根据 pms_attr 获取 catelog_id 的数据
        // 不要 pms_attr_attrgroup_relation 中

        // 1. 根据 attrGroupId 在 pms_attr_group 里获取 catelog_id
        val attrGroupEntity = KtQueryChainWrapper(AttrGroupEntity::class.java)
            .eq(AttrGroupEntity::attrGroupId, attrGroupId)
            .one()
        // 2. 当前分类 catelog_id 的所有分组 的ID
        val idsList = KtQueryChainWrapper(AttrGroupEntity::class.java)
            .eq(AttrGroupEntity::catelogId, attrGroupEntity.catelogId)
            .list()//这里肯定不可能为空，两次都是通过AttrGroup来查的
            .map {
                it.attrGroupId
            }
        // 3. 查询这些分组 已经关联的 属性
        val attrIdList = KtQueryChainWrapper(AttrAttrgroupRelationEntity::class.java)
            .`in`(AttrAttrgroupRelationEntity::attrGroupId, idsList)
            .list()//这里可能为空，有可能所有分组都还没关联属性
            ?.mapNotNull {
                it.attrId
            }

        //4.最终去查询符合条件的属性
        val key = params["key"] as? String?
        return KtQueryChainWrapper(AttrEntity::class.java)
            .eq(AttrEntity::catelogId, attrGroupEntity.catelogId)
            .eq(AttrEntity::attrType, ProductConstants.AttrEnum.ATTR_TYPE_BASE.code)//销售属性没有分组，所以不用关联
            .notIn(!attrIdList.isNullOrEmpty(), AttrEntity::attrId, attrIdList) //不为null或空时，查的ID不能是attrIdList里的
            //key 不是 空时添加模糊查询条件
            .and(!key.isNullOrBlank()) {
                it.eq(AttrEntity::attrId, key).or().like(AttrEntity::attrName, key)
            }
            .page(Query<AttrEntity>().getPage(params))
            .pageUtils()
    }

    /**
     * 根据分类ID查询出所有分组已经分组下的所有属性
     */
    override fun getAttrGroupWithAttrsByCatelogId(catelogId: Long): List<AttrGroupWithAttrsVO> {
        //查询分类ID的所有的分组
        val attrGroupList = KtQueryChainWrapper(AttrGroupEntity::class.java)
            .eq(AttrGroupEntity::catelogId, catelogId)
            .list()
        val vos = mutableListOf<AttrGroupWithAttrsVO>()
        attrGroupList.forEach { attrGroupEntity ->
            // copy 数据到 vo
            val vo = AttrGroupWithAttrsVO()
            BeanUtils.copyProperties(attrGroupEntity, vo)

            attrGroupEntity.attrGroupId?.let {
                //这里有：教程的方法是在AttrService，我这在AttrGroupService
                vo.attrs = getRelationAttrs(it)
            }
            vos.add(vo)
        }
        return vos
    }


    /**
     * 根据分组ID ， 查询出所有的分组数据
     *
     */
    override fun getRelationAttrs(attrGroupId: Long): List<AttrEntity> {
        //通过中间表 查询指定attrGroupId的所有attrId
        val idList = KtQueryChainWrapper(AttrAttrgroupRelationEntity::class.java)
            .eq(AttrAttrgroupRelationEntity::attrGroupId, attrGroupId)
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

    /**
     * 根据spuId 获取属性分组信息
     *
     * 查出当前 spuId 对应的 所有属性的分组信息以及当前分组下的所有属性对应的值
     *
     * ```sql
     *
     * ```
     */
    override fun getAttrGroupWithAttrsBySpuId(spuId: Long, catalogId: Long): List<SpuItemAttrGroupVo> {
        return this.baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId)
    }

}