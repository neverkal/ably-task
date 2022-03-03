package com.ably.task.member.api.service;

import com.ably.task.member.api.dto.request.AuthOtpReq;
import com.ably.task.member.api.dto.request.AuthOtpSendReq;
import com.ably.task.member.api.dto.request.MemberPasswordResetReq;
import com.ably.task.member.api.dto.request.UserLoginReq;
import com.ably.task.member.api.dto.response.AuthOtpSendRes;
import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.entity.OtpAuth;
import com.ably.task.member.api.entity.type.AuthType;
import com.ably.task.member.api.entity.type.OtpAuthType;
import com.ably.task.member.api.repository.MemberRepository;
import com.ably.task.member.api.repository.OtpAuthRepository;
import com.ably.task.member.api.repository.OtpAuthRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final OtpAuthRepository otpAuthRepository;
    private final OtpAuthRepositorySupport otpAuthRepositorySupport;
    private final PasswordEncoder passwordEncoder;

    public Long memberJoin(Member member) {
        String phoneNumber = member.getPhoneNumber();
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(10);

        OtpAuthType otpAuthType = otpAuthRepositorySupport.otpAuthPhoneNumber(
                phoneNumber, localDateTime, AuthType.JOIN);

        if (otpAuthType == null) {
            throw new IllegalArgumentException("휴대폰 인증을 진행해주시기 바랍니다.");
        }

        return memberRepository.save(member).getId();
    }

    public boolean memberLogin(UserLoginReq request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return true;
    }

    public AuthOtpSendRes otpSend(AuthOtpSendReq authOtpSendReq) {
        // 인증번호 생성
        Random random = new Random();
        int createNum = 0;
        int letter = 4;
        String ranNumber = "";
        String resultNumber = "";

        for (int letterIndex = 0; letterIndex < letter; letterIndex++) {
            createNum = random.nextInt(9);
            ranNumber = Integer.toString(createNum);
            resultNumber += ranNumber;
        }

        OtpAuth otpAuth = OtpAuth.builder()
                .phoneNumber(authOtpSendReq.getPhoneNumber())
                .otpAuthType(OtpAuthType.PROCESS)
                .authType(authOtpSendReq.getAuthType())
                .createAt(LocalDateTime.now())
                .otpNumber(Integer.parseInt(resultNumber))
                .build();

        long id = otpAuthRepository.save(otpAuth).getId();

        AuthOtpSendRes authOtpSendRes = AuthOtpSendRes.builder()
                .otp(Integer.parseInt(resultNumber))
                .id(id)
                .build();

        return authOtpSendRes;
    }

    public boolean otpCheck(AuthOtpReq authOtpReq) {
        OtpAuth otpAuth = otpAuthRepository.findFirstByIdAndOtpNumberAndOtpAuthType(
                authOtpReq.getOtpAuthId(),
                authOtpReq.getOtpNumber(),
                OtpAuthType.PROCESS
        ).orElseThrow(() -> new IllegalArgumentException("인증정보가 존재하지 않습니다."));

        otpAuth.setOtpAuthType(OtpAuthType.SUCCESS);
        otpAuth.setModifiedAt(LocalDateTime.now());

        otpAuthRepository.save(otpAuth);

        return true;
    }

    public boolean passwordReset(MemberPasswordResetReq memberPasswordResetReq) {
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(10);

        String phoneNumber = otpAuthRepositorySupport.otpAuthPhoneNumberPassword(
                memberPasswordResetReq.getAuthOtpId(),
                localDateTime,
                AuthType.PASSWORD
        );

        if (phoneNumber == null) {
            throw new IllegalArgumentException("휴대폰 인증을 진행해주시기 바랍니다.");
        }

        Member member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        member.setPassword(passwordEncoder.encode(memberPasswordResetReq.getPassword()));
        memberRepository.save(member);

        return true;
    }

}
