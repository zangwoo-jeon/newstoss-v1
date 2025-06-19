package com.newstoss.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {
    @Id
    private UUID memberId;

    @Column(name = "account", nullable = false, length = 20, unique = true)
    private String account;
    @Column(name = "password", nullable = false, length = 60)
    private String password;
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(length = 40)
    private String email;
    @Column(name = "invest_score")
    private long investScore;
    @Column(name = "fg_offset")
    private UUID fgOffset;
    @Embedded
    private Address address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한(Roles)을 반환합니다.
        // 예: List.of(new SimpleGrantedAuthority("ROLE_USER"));
        // 실제 애플리케이션의 권한 관리에 맞게 구현해야 합니다.
        // 뉴스 로그 조회가 필요한 경우, 최소한 기본 권한이라도 반환해야 Authentication 객체가 유효해집니다.
        return List.of(); // 현재는 권한이 없으므로 빈 리스트 반환
    }

    @Override
    public String getPassword() {
        return this.password; // 비밀번호 필드 반환
    }

    @Override
    public String getUsername() {
        // 사용자의 식별자 (Principal의 이름)를 반환합니다.
        // account, memberId.toString(), email 등 어떤 것을 사용할지 결정합니다.
        // @AuthenticationPrincipal Member member 로 받을 때는 Member 객체 전체가 오므로,
        // 이 메서드의 반환 값이 직접적으로 Principal의 이름이 되는 것은 아니지만, UserDetails의 규약입니다.
        // 보통 고유한 값 (account 또는 memberId.toString())을 사용합니다.
        return this.account; // 예시: account를 UserDetails의 username으로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (항상 true로 설정 가능)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (항상 true로 설정 가능)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(비밀번호 등) 만료 여부 (항상 true로 설정 가능)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 (항상 true로 설정 가능)
    }

    public void changeFgOffset(UUID newOffset) {
        if (newOffset == null) {
            throw new IllegalArgumentException("FG offset cannot be null");
        }
        if (!newOffset.equals(this.fgOffset)) {
            this.fgOffset = newOffset;
        }
    }

    public void changeInvestScore(long newScore) {
        if (newScore < 0) {
            throw new IllegalArgumentException("투자 점수는 0 이상이어야 합니다.");
        }
        if (this.investScore != newScore) {
            this.investScore = newScore;
        }
    }

}
