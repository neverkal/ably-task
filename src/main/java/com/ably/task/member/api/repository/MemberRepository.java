package com.ably.task.member.api.repository;

import com.ably.task.member.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhoneNumber(String phoneNumber);

}
