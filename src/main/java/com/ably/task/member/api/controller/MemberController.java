package com.ably.task.member.api.controller;

import com.ably.task.member.api.dto.response.MemberInfoRes;
import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.repository.MemberRepository;
import com.ably.task.member.api.service.MemberService;
import com.ably.task.member.common.ApiResponse;
import com.ably.task.member.config.security.token.JwtTokenProvider;
import com.ably.task.member.config.security.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> memberInfo() {

        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 존재하지 않습니다."));

        MemberInfoRes memberInfoRes = MemberInfoRes.builder()
                .phoneNumber(member.getPhoneNumber())
                .personName(member.getPersonName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .build();

        ApiResponse apiResponse = ApiResponse.success("member", memberInfoRes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);
    }


}
