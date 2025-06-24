package com.newstoss.stock.application.port.in.v1;

import com.newstoss.stock.adapter.inbound.dto.response.v1.CategoryPageResponseDto;

import java.util.List;

public interface GetCategoryUseCase {
    /**
     * 카테고리 정보를 조회하는 메서드
     *
     * @return 카테고리 정보 리스트
     */
    List<String> getCategories();
    /**
     * 카테고리별 주식 정보를 조회하는 메서드
     *
     * @param category 카테고리 이름
     * @param page 페이지 번호 (0부터 시작)
     * @return 해당 카테고리에 속하는 주식 정보 리스트
     */
    CategoryPageResponseDto getStockByCategory(String category, int page);
}
