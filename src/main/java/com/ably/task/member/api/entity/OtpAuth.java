package com.ably.task.member.api.entity;

import com.ably.task.member.api.entity.type.AuthType;
import com.ably.task.member.api.entity.type.OtpAuthType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OtpAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpAuthType otpAuthType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthType authType;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String otpNumber;

}
