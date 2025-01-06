package com.vurtnewk.mall.member.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.vurtnewk.mall.member.entity.MemberReceiveAddressEntity
import com.vurtnewk.mall.member.service.MemberReceiveAddressService
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.R

/**
 * 会员收货地址
 *
 * @author vurtnewk
 * @email vurtnewk@gmail.com
 * @date 2025-01-06 14:35:26
 */
@RestController
@RequestMapping("member/memberreceiveaddress")
class MemberReceiveAddressController @Autowired constructor(
        private val memberReceiveAddressService: MemberReceiveAddressService
) {

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:memberreceiveaddress:list")
    fun list(@RequestParam params: Map<String, Any>): R {
        val page: PageUtils = memberReceiveAddressService.queryPage(params)
        return R.ok().put("page", page)
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:memberreceiveaddress:info")
    fun info(@PathVariable("id") id: Long): R {
        val memberReceiveAddress: MemberReceiveAddressEntity = memberReceiveAddressService.getById(id)
        return R.ok().put("memberReceiveAddress", memberReceiveAddress)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:memberreceiveaddress:save")
    fun save(@RequestBody memberReceiveAddress: MemberReceiveAddressEntity): R {
            memberReceiveAddressService.save(memberReceiveAddress)
        return R.ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:memberreceiveaddress:update")
    fun update(@RequestBody memberReceiveAddress: MemberReceiveAddressEntity): R {
            memberReceiveAddressService.updateById(memberReceiveAddress)
        return R.ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:memberreceiveaddress:delete")
    fun delete(@RequestBody ids: Array<Long>): R {
            memberReceiveAddressService.removeByIds(ids.asList())
        return R.ok()
    }
}