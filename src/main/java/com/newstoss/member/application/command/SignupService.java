package com.newstoss.member.application.command;

import com.newstoss.global.handler.CustomException;
import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.member.adapter.in.web.dto.requestDTO.SignupRequestDTO;
import com.newstoss.member.domain.Address;
import com.newstoss.member.domain.Member;
import com.newstoss.member.domain.MemberCommandPort;
import com.newstoss.member.domain.MemberQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final MemberCommandPort memberCommandPort;

    public Member exec(SignupRequestDTO signupRequestDTO){
        Member member = Member.builder()
                .memberId(UUID.randomUUID())
                .account(signupRequestDTO.getAccount())
                .password(signupRequestDTO.getPassword())
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
        return memberCommandPort.save(member);
    }
}
