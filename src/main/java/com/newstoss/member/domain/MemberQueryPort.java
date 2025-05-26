package com.newstoss.member.domain;

import java.util.Optional;
import java.util.UUID;

public interface MemberQueryPort {
    Optional<Member> findById(UUID memberId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByAccount(String account);

}
