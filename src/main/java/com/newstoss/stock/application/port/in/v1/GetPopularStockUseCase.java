package com.newstoss.stock.application.port.in.v1;

import com.newstoss.stock.adapter.outbound.kis.dto.KisPopularDto;

import java.util.List;

public interface GetPopularStockUseCase {
    /**
     * 최대 10개의 인기 주식 정보를 가져온다.
     * @return 인기 주식 정보 리스트
     */
    List<KisPopularDto> getPopularStock();
}
