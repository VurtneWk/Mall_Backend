package com.vurtnewk.mall.product.service

import com.baomidou.mybatisplus.extension.service.IService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.mall.product.entity.AttrEntity
import com.vurtnewk.mall.product.vo.AttrRespVO
import com.vurtnewk.mall.product.vo.AttrVO

/**
 * 商品属性
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 12:58:45
 */
interface AttrService : IService<AttrEntity> {

    fun queryPage(params: Map<String, Any> ): PageUtils
    fun saveAttrVO(attr: AttrVO)
    fun queryBaseAttrPage(params: Map<String, Any>, catelogId: Long, attrType: String): PageUtils
    fun getAttrInfo(attrId: Long): AttrRespVO
    fun updateAttrVO(attrVo: AttrVO)
}

