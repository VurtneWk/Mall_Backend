package com.vurtnewk.mall.ware.dao

import com.vurtnewk.mall.ware.entity.WareInfoEntity
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 * 仓库信息
 * 
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:43:38
 */
@Mapper
interface WareInfoDao : BaseMapper<WareInfoEntity> {
	
}
