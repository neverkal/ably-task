package com.ably.task.member.api.dto.request;

import com.ably.task.member.api.utils.RegexPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthOtpReq {
    @NotNull(message = "OTP 인증을 진행해주세요.")
    long otpAuthId;

    @NotNull(message = "OTP 번호를 입력해주세요.")
    @Pattern(regexp = RegexPattern.OTP, message = "인증번호 4자리를 입력해주세요.")
    String otpNumber;
}
