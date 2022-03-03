package com.ably.task.member.api.service;

import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.repository.MemberRepository;
import com.ably.task.member.api.repository.OtpAuthRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OtpAuthRepositorySupport otpAuthRepositorySupport;

    public Member registerMember(Member member) {


        memberRepository.save(member);
        return member;
    }
}
