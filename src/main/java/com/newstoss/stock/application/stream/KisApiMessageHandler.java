package com.newstoss.stock.application.stream;

import com.newstoss.global.kis.dto.KisApiRequestDto;

public interface KisApiMessageHandler {
    boolean supports(String type);
    void handle(KisApiRequestDto dto);
}
