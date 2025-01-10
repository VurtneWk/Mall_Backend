package com.vurtnewk.mall.product.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query
import com.vurtnewk.common.utils.ext.logDebug
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.product.dao.AttrAttrgroupRelationDao

import com.vurtnewk.mall.product.dao.AttrDao
import com.vurtnewk.mall.product.entity.AttrAttrgroupRelationEntity
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.service.AttrService
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
}