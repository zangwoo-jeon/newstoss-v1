package com.newstoss.stock.adapter.inbound.api;

import com.newstoss.stock.adapter.inbound.api.dto.response.IndicesResponseDto;
import com.newstoss.stock.application.port.in.GetIndiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockApiController {
    private GetIndiceUseCase getIndiceUseCase;
    // 주요 지수 일자별 조회
    @GetMapping("/indices/{market}")
    public EntityResponse<List<IndicesResponseDto>> getIndicesByMarket(@PathVariable String market,
                                                                       @RequestParam LocalDateTime startDate,
                                                                       @RequestParam LocalDateTime endDate) {

    }

}
