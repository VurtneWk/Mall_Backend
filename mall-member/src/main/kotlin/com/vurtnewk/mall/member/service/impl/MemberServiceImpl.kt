package com.vurtnewk.mall.member.service.impl

import com.alibaba.fastjson2.JSON
import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.vurtnewk.common.utils.HttpUtils
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.member.dao.MemberDao
import com.vurtnewk.mall.member.dao.MemberLevelDao
import com.vurtnewk.mall.member.entity.MemberEntity
import com.vurtnewk.mall.member.excetion.PhoneExistException
import com.vurtnewk.mall.member.excetion.UsernameExistException
import com.vurtnewk.mall.member.service.MemberService
import com.vurtnewk.mall.member.vo.MemberLoginVo
import com.vurtnewk.mall.member.vo.MemberRegisterVo
import com.vurtnewk.mall.member.vo.SocialUser
import org.apache.http.util.EntityUtils
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

    /**
     * 登录
     */
    override fun login(memberLoginVo: MemberLoginVo): MemberEntity? {
        val memberEntity = KtQueryChainWrapper(MemberEntity::class.java)
            .eq(MemberEntity::username, memberLoginVo.loginAcct)
            .or()
            .eq(MemberEntity::mobile, memberLoginVo.loginAcct)
            .one()
        memberEntity?.id ?: return null
        //匹配查询的数据库里的密码
        val matches = BCryptPasswordEncoder().matches(memberLoginVo.password, memberEntity.password)
        if (!matches) return null
        return memberEntity
    }

    override fun login(socialUser: SocialUser): MemberEntity? {
        val memberEntity = KtQueryChainWrapper(MemberEntity::class.java)
            .eq(MemberEntity::socialUid, socialUser.uid)
            .one()
        if (memberEntity != null) { //登录过
            //更新令牌和时间
            KtUpdateChainWrapper(MemberEntity::class.java)
                .set(MemberEntity::accessToken, socialUser.access_token)
                .set(MemberEntity::expiresIn, socialUser.expires_in)
                .eq(MemberEntity::id, memberEntity.id)
                .update()
            memberEntity.accessToken = socialUser.access_token
            memberEntity.expiresIn = socialUser.expires_in
            return memberEntity
        } else {//注册
            val registerMemberEntity = MemberEntity()
            // 查出社交用户的社交账号信息 昵称、性别等
            runCatching {
                val queryMap = hashMapOf(
                    "access_token" to socialUser.access_token,
                    "uid" to socialUser.uid
                )
                val response = HttpUtils.doGet(
                    "https://api.weibo.com",
                    "/2/users/show.json", "GET",
                    hashMapOf<String, String>(),
                    queryMap
                )
                if (response.statusLine.statusCode == 200) {
                    val json = EntityUtils.toString(response.entity)
                    val jsonObject = JSON.parseObject(json)
                    registerMemberEntity.nickname = jsonObject.getString("name")
                    registerMemberEntity.gender = if (jsonObject.getString("gender") == "m") 1 else 0
                }
            }
            //...
            registerMemberEntity.accessToken = socialUser.access_token
            registerMemberEntity.expiresIn = socialUser.expires_in
            registerMemberEntity.socialUid = socialUser.uid
            this.baseMapper.insert(registerMemberEntity)
            return registerMemberEntity
        }
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