package com.ably.task.member.api.controller;

import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.repository.MemberRepository;
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

import java.nio.charset.Charset;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> memberInfo() {

        log.info("get member" + SecurityUtil.getCurrentMemberId());

        Optional<Member> member = memberRepository.findByEmail(SecurityUtil.getCurrentMemberId());

        ApiResponse apiResponse = ApiResponse.success("member", member.get());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);
    }


}
