package com.vurtnewk.mall.member.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberDao
import com.vurtnewk.mall.member.dao.MemberLevelDao
import com.vurtnewk.mall.member.entity.MemberEntity
import com.vurtnewk.mall.member.excetion.PhoneExistException
import com.vurtnewk.mall.member.excetion.UsernameExistException
import com.vurtnewk.mall.member.service.MemberService
import com.vurtnewk.mall.member.vo.MemberRegisterVo
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*


@Service("memberService")
class MemberServiceImpl(
    private val memberLevelDao: MemberLevelDao,
) : ServiceImpl<MemberDao, MemberEntity>(), MemberService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<MemberEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    /**
     * 注册
     */
    override fun register(memberRegisterVo: MemberRegisterVo) {
        val memberEntity = MemberEntity()
        // 手机号和用户名的唯一性
        // 为了让controller能感知异常，异常机制
        this.checkPhoneUnique(memberRegisterVo.phone)
        this.checkUserNameUnique(memberRegisterVo.userName)

        memberEntity.username = memberRegisterVo.userName
        memberEntity.mobile = memberRegisterVo.phone

        // 会员注册时的默认 会员等级
        val levelEntity = memberLevelDao.getDefaultLevel()
        memberEntity.levelId = levelEntity.id

        //password 需要加密 BCryptPasswordEncoder 已自动进行了加盐
        memberEntity.password = BCryptPasswordEncoder().encode(memberRegisterVo.password)

        memberEntity.createTime = Date()

        this.baseMapper.insert(memberEntity)
    }

    override fun checkPhoneUnique(phone: String) {
        val count = KtQueryChainWrapper(MemberEntity::class.java)
            .eq(MemberEntity::mobile, phone)
            .count()
        if (count > 0) {
            throw PhoneExistException()
        }
    }

    override fun checkUserNameUnique(userName: String) {
        KtQueryChainWrapper(MemberEntity::class.java)
            .eq(MemberEntity::username, userName)
            .count()
            .also {
                if (it > 0) {
                    throw UsernameExistException()
                }
            }
    }
}