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
public class AuthLoginReq {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = RegexPattern.PASSWORD, message = "비밀번호 형식이 올바르지 않습니다.")
    String password;
}
