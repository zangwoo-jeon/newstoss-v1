package com.newstoss.stock.adapter.inbound.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicesResponseDto {

    private LocalDateTime Date;
    private Float indice_price;

}
