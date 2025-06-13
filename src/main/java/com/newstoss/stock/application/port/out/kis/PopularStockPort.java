package com.newstoss.stock.application.port.out.kis;

import com.newstoss.stock.adapter.outbound.kis.dto.KisPopularDto;
import java.util.List;

public interface PopularStockPort {
    List<KisPopularDto> getPopularStock();
}
