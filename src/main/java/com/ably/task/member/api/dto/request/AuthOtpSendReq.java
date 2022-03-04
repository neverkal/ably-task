package com.ably.task.member.api.dto.request;

import com.ably.task.member.api.entity.type.AuthType;
import com.ably.task.member.api.utils.RegexPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthOtpSendReq {

    @NotNull(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = RegexPattern.PHONE_NUMBER, message = "휴대폰 번호 형식이 올바르지 않습니다.(01012345678)")
    String phoneNumber;
    AuthType authType;
}
