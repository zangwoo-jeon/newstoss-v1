package com.newstoss.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
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
    @Column(name = "fg_offset")
    private UUID fgOffset;
    @Embedded
    private Address address;

    public void changeFgOffset(UUID newOffset) {
        if (newOffset == null) {
            throw new IllegalArgumentException("FG offset cannot be null");
        }
        if (!newOffset.equals(this.fgOffset)) {
            this.fgOffset = newOffset;
        }
    }
}
