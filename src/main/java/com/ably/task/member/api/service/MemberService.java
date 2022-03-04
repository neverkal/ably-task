package com.ably.task.member.api.service;

import com.ably.task.member.api.dto.response.MemberInfoRes;
import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.repository.MemberRepository;
import com.ably.task.member.config.security.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberInfoRes joinMemberInfo() {
        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 조회되지 않습니다."));

        return MemberInfoRes.builder()
                .email(member.getEmail())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .personName(member.getPersonName())
                .build();
    }
}
