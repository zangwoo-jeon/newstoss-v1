package com.newstoss.stock.adapter.outbound.kis.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.stock.adapter.outbound.kis.dto.KisFxInfoDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisFxPastInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KisFxDto {

    @JsonProperty("output1")
    private KisFxInfoDto output1;

    @JsonProperty("output2")
    private List<KisFxPastInfoDto> output2;

}
