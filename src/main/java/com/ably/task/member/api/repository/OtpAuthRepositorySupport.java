package com.ably.task.member.api.repository;

import static com.ably.task.member.api.entity.QOtpAuth.otpAuth;
import com.ably.task.member.api.entity.OtpAuth;
import com.ably.task.member.api.entity.type.AuthType;
import com.ably.task.member.api.entity.type.OtpAuthType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
@Slf4j
public class OtpAuthRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public OtpAuthRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(OtpAuth.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public OtpAuthType otpAuthPhoneNumber(String phoneNumber, LocalDateTime localDateTime, AuthType authType) {
        return jpaQueryFactory
                .select(otpAuth.otpAuthType)
                .from(otpAuth)
                .where(
                        otpAuth.phoneNumber.eq(phoneNumber)
                                .and(otpAuth.authType.eq(authType))
                                .and(otpAuth.otpAuthType.eq(OtpAuthType.SUCCESS))
                                .and(otpAuth.modifiedAt.goe(localDateTime))
                )
                .fetchOne();
    }

    public String otpAuthPhoneNumberPassword(long otpAuthId, LocalDateTime localDateTime, AuthType authType) {
        return jpaQueryFactory
                .select(otpAuth.phoneNumber)
                .from(otpAuth)
                .where(
                        otpAuth.id.eq(otpAuthId)
                                .and(otpAuth.authType.eq(authType))
                                .and(otpAuth.otpAuthType.eq(OtpAuthType.SUCCESS))
                                .and(otpAuth.modifiedAt.goe(localDateTime))
                )
                .fetchOne();
    }

}
