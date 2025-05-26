package com.newstoss.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {

    @Column(name = "zipcode", length = 10)
    private String zipcode;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "address_detail", length = 100)
    private String addressDetail;
}
