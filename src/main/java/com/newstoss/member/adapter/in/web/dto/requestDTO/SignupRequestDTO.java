package com.newstoss.member.adapter.in.web.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
    private String account;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private UUID fgOffset;
    private AddressDTO address;
}
