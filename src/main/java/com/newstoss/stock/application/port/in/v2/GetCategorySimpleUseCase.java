package com.newstoss.stock.application.port.in.v2;

import com.newstoss.stock.adapter.inbound.dto.response.v2.CategoryPageDto;

public interface GetCategorySimpleUseCase {

    CategoryPageDto getCategorySimple(String category, int page);
}
