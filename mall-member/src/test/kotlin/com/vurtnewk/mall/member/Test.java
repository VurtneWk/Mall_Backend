package com.vurtnewk.mall.member;

import com.vurtnewk.mall.member.service.MemberService;
import com.vurtnewk.mall.member.service.impl.MemberServiceImpl;

import java.io.IOException;

/**
 *
 * @author vurtnewk
 * @since 2025/1/21 01:37
 */
public class Test {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl(null);
    }
}
