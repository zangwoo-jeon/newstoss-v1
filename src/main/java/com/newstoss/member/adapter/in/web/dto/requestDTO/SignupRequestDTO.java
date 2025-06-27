package com.newstoss.member.adapter.in.web.dto.requestDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    private String account;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotNull(message = "fgOffset는 필수입니다.")
    private UUID fgOffset;

    @Valid // address 내부 필드들도 검증하고 싶으면 이거 필요
    @NotNull(message = "주소는 필수입니다.")
    private AddressDTO address;
}

