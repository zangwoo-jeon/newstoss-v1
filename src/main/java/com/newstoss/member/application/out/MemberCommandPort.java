package com.newstoss.member.application.out;

import com.newstoss.member.domain.Member;

import java.util.UUID;


public interface MemberCommandPort {
    Member save(Member member);
    void deleteById(UUID memberId);
}
