package com.newstoss.member.adapter.out.persistence;

import com.newstoss.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface JpaMemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByAccount(String account);
    Optional<Member> findByEmail(String email);

    List<Member> findByName(String name);
}
