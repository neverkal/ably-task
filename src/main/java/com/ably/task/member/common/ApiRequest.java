package com.ably.task.member.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRequest<T> {
    private T data;
}
