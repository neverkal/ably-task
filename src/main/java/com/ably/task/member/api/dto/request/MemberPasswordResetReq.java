package com.ably.task.member.api.dto.request;

import com.ably.task.member.api.utils.RegexPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordResetReq {
    @NotNull(message = "OTP 인증을 진행해주세요.")
    long authOtpId;

    @NotNull(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = RegexPattern.PASSWORD, message = "비밀번호를 올바르게 입력해주세요.")
    String password;
}
