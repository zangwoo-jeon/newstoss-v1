package com.newstoss.member.domain;

import java.util.UUID;

public interface MemberCommandPort {
    Member save(Member member);
    void deleteById(UUID memberId);
}
