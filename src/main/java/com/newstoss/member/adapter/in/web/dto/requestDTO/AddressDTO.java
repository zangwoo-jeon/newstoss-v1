package com.newstoss.member.adapter.in.web.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String zipcode;
    private String address;
    private String addressDetail;
}
