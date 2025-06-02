package com.newstoss.member.application.out;

import com.newstoss.member.domain.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberQueryPort {
    Optional<Member> findById(UUID memberId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByAccount(String account);

}
