package com.newstoss.member.application.in.command;

import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.domain.Member;
import com.newstoss.member.domain.UserAccount;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaMemberRepository memberRepository;

    public CustomUserDetailsService(JpaMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다"));

        // 권한이 별도로 없다면 ROLE_USER로 고정
        return new UserAccount(
                member.getMemberId(),
                member.getAccount(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
} 