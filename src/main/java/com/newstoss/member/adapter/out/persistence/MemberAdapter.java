package com.newstoss.member.adapter.out.persistence;

import com.newstoss.member.domain.Member;
import com.newstoss.member.domain.MemberCommandPort;
import com.newstoss.member.domain.MemberQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
@RequiredArgsConstructor
public class MemberAdapter implements MemberCommandPort, MemberQueryPort {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Member save(Member member) {
        return jpaMemberRepository.save(member);
    }

    @Override
    public void deleteById(UUID memberId) {
        jpaMemberRepository.deleteById(memberId);
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByAccount(String account) {
        return jpaMemberRepository.findByAccount(account);
    }

}
