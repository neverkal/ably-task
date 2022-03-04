package com.ably.task.member.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoRes {
    String personName;
    String email;
    String nickName;
    String phoneNumber;
}
