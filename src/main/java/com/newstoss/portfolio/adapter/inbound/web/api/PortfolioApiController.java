package com.newstoss.portfolio.adapter.inbound.web.api;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.portfolio.adapter.inbound.web.dto.ChangePortfolioRequestDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.request.CreatePortfolioRequestDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.MemberPnlPeriodResponseDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioDailyPnlResponseDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;
import com.newstoss.portfolio.application.port.in.AddPortfolioUseCase;
import com.newstoss.portfolio.application.port.in.CreatePortfolioStockUseCase;
import com.newstoss.portfolio.application.port.in.GetPortfolioStockUseCase;
import com.newstoss.portfolio.application.port.in.SellPortfolioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "포트폴리오 API", description = "포트폴리오 관련 API")
@RequestMapping("/api/v1/portfolios")
public class PortfolioApiController {

    private final CreatePortfolioStockUseCase createPortfolio;
    private final AddPortfolioUseCase addPortfolio;
    private final SellPortfolioUseCase sellPortfolio;
    private final GetPortfolioStockUseCase getPortfolioStockUseCase;

    @Operation(
            summary = "포트폴리오 주식 생성",
            description = "사용자의 포트폴리오 주식를 생성합니다. 주식 코드, 주식 수량, 매입가를 입력받습니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "포트폴리오 손익 기간 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PortfolioStocksResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류"
                    )
            }
    )
    @PostMapping("/{memberId}")
    public ResponseEntity<?> createPortfolio(@PathVariable("memberId") UUID memberId,
                                             @RequestBody CreatePortfolioRequestDto dto) {
        PortfolioStocksResponseDto portfolio = createPortfolio.createPortfolioStock(memberId, dto.getStockCode(), dto.getStockCount(), dto.getEntryPrice());
        return ResponseEntity.ok(new SuccessResponse<>(true, "포트폴리오 생성 성공", portfolio));
    }

    @Operation(
            summary = "포트폴리오 주식 변경",
            description = "사용자의 포트폴리오를 변경합니다. 주식 코드, 주식 수량, 매입가를 입력받습니다. " +
                    "추가할 경우에는 isAdd에 true를 입력하고, 판매할 경우에는 isAdd에 false를 입력합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "포트폴리오 손익 기간 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PortfolioStocksResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류"
                    )
            }

    )
    @PostMapping("{memberId}/{stockCode}")
    public ResponseEntity<?> changePortfolio(@PathVariable("memberId") UUID memberId,
                                                            @PathVariable("stockCode") String stockCode,
                                                            @RequestBody ChangePortfolioRequestDto dto) {
        PortfolioStocksResponseDto responseDto;
        String message;
        if (dto.isAdd()) {
            responseDto = addPortfolio.addPortfolio(memberId, stockCode, dto.getStockCount(), dto.getPrice());
        } else {
            responseDto = sellPortfolio.sellStock(memberId, stockCode, dto.getStockCount(), dto.getPrice());
        }
        if (responseDto == null) {message = "포트폴리오에서 주식을 삭제하였습니다.";}
        else {message = "포트폴리오 변경 성공";}
        return ResponseEntity.ok(new SuccessResponse<>(true, message, responseDto));
    }

    @Operation(
            summary = "포트폴리오 조회",
            description = "사용자의 포트폴리오에 포함된 주식 목록을 조회합니다. " +
                    "포트폴리오가 없는 경우 빈 리스트를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "포트폴리오 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PortfolioDailyPnlResponseDto.class)
                            )
                    ),
            }
    )
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getPortfolios(@PathVariable("memberId") UUID memberId) {
        log.info("Get portfolios for member {}", memberId);
        PortfolioDailyPnlResponseDto portfolioDto = getPortfolioStockUseCase.getPortfolioStocks(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "포트폴리오 조회 성공", portfolioDto));
    }
}
