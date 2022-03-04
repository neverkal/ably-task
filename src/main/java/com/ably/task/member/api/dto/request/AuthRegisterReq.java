package com.ably.task.member.api.dto.request;

import com.ably.task.member.api.utils.RegexPattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegisterReq {
    @NotNull(message = "이름을 입력해주세요.")
    @Pattern(regexp = RegexPattern.NAME, message = "이름 형식이 올바르지 않습니다.")
    String personName;

    @NotNull(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email;

    @NotNull(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = RegexPattern.NICK_NAME,
            message = "닉네임 형식이 올바르지 않습니다.(영어/한글/숫자 최대 20자)")
    String nickName;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = RegexPattern.PASSWORD,
            message = "비밀번호 형식이 올바르지 않습니다.")
    String password;

    @NotNull(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = RegexPattern.PHONE_NUMBER, message = "휴대폰 번호 형식이 올바르지 않습니다.(01012345678)")
    String phoneNumber;

    @NotNull(message = "휴대폰 번호 인증 아이디를 입력해주세요.")
    long otpAuthId;
}
