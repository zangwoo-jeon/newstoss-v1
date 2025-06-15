package com.newstoss.member.application.in.command;

import com.newstoss.member.adapter.in.web.dto.requestDTO.SignupRequestDTO;
import com.newstoss.member.domain.Address;
import com.newstoss.member.domain.Member;
import com.newstoss.member.application.out.MemberCommandPort;
import com.newstoss.member.domain.MemberSignUpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final MemberCommandPort memberCommandPort;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;

    public Member exec(SignupRequestDTO signupRequestDTO){
        String passwordHash = passwordEncoder.encode(signupRequestDTO.getPassword());

        Member member = Member.builder()
                .memberId(UUID.randomUUID())
                .account(signupRequestDTO.getAccount())
                .password(passwordHash)
                .name(signupRequestDTO.getName())
                .phoneNumber(signupRequestDTO.getPhoneNumber())
                .email(signupRequestDTO.getEmail())
                .fgOffset(signupRequestDTO.getFgOffset())
                .address(Address.builder()
                        .zipcode(signupRequestDTO.getAddress().getZipcode())
                        .address(signupRequestDTO.getAddress().getAddress())
                        .addressDetail(signupRequestDTO.getAddress().getAddressDetail())
                        .build())
                .build();

        publisher.publishEvent(new MemberSignUpEvent(member.getMemberId()));

        return memberCommandPort.save(member);
    }
}
