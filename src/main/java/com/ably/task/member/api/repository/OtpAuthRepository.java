package com.ably.task.member.api.repository;

import com.ably.task.member.api.entity.OtpAuth;
import com.ably.task.member.api.entity.type.AuthType;
import com.ably.task.member.api.entity.type.OtpAuthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpAuthRepository extends JpaRepository<OtpAuth, Long> {

    Optional<OtpAuth> findFirstByIdAndOtpNumberAndOtpAuthType(
            long id, int otpNumber, OtpAuthType otpAuthType);

    Optional<OtpAuth> findFirstByIdAndAuthTypeAndOtpAuthType(
            long id, AuthType authType, OtpAuthType otpAuthType
    );
}
