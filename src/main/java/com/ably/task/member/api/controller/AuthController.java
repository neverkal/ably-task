package com.ably.task.member.api.controller;

import com.ably.task.member.api.dto.request.*;
import com.ably.task.member.api.dto.response.AuthOtpSendRes;
import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.repository.MemberRepository;
import com.ably.task.member.api.service.AuthService;
import com.ably.task.member.common.ApiRequest;
import com.ably.task.member.common.ApiResponse;
import com.ably.task.member.config.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> join(@RequestBody @Valid UserRegisterReq request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .nickName(request.getNickName())
                .phoneNumber(request.getPhoneNumber())
                .personName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        Long userId = authService.memberJoin(member);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ApiResponse apiResponse = ApiResponse.success("userId", userId);

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid UserLoginReq request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        boolean loginValidate = authService.memberLogin(request);
        String token = (loginValidate) ? jwtTokenProvider.createToken(member.getUsername(), member.getRoles()) : "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ApiResponse apiResponse = ApiResponse.success("token", token);

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);

    }

    @PostMapping("/otp")
    public ResponseEntity<ApiResponse> otpSend(@RequestBody @Valid AuthOtpSendReq request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        AuthOtpSendRes authOtpSendRes = authService.otpSend(request);
        ApiResponse apiResponse = ApiResponse.success("auth_info", authOtpSendRes);

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);
    }

    @PutMapping("/otp")
    public ResponseEntity<ApiResponse> otpAuth(@RequestBody @Valid AuthOtpReq request) {
        boolean othCheck = authService.otpCheck(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ApiResponse apiResponse = ApiResponse.success("otp_auth", othCheck);

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse> passwordReset(@RequestBody @Valid MemberPasswordResetReq request) {
        boolean resetPasswordResult = authService.passwordReset(request);

        log.info("reset password = {}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ApiResponse apiResponse = ApiResponse.success("password_reset", resetPasswordResult);

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);
    }

}
