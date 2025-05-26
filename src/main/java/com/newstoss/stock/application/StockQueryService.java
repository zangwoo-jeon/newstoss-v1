package com.newstoss.stock.application;

import com.newstoss.stock.application.port.in.*;
import org.springframework.stereotype.Service;

@Service
public class StockQueryService implements GetStockSearchUseCase, GetCategoryUseCase,
        GetPopularStockUseCase, GetStockInfoUseCase {

}
