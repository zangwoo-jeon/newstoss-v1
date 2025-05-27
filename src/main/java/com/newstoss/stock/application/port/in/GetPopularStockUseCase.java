package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.outbound.kis.dto.response.KisListOutputDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPopularDto;

import java.util.List;

public interface GetPopularStockUseCase {
    /**
     * 인기 주식 정보를 조회하는 메서드
     *
     * @return 인기 주식 정보 리스트
     */
    KisListOutputDto<KisPopularDto> getPopularStock();
}
