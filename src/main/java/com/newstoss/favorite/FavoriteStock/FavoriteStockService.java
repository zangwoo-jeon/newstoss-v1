package com.newstoss.favorite.FavoriteStock;

import com.newstoss.favorite.FavoriteGroup.Favorite;
import com.newstoss.favorite.FavoriteGroup.FavoriteRepository;
import com.newstoss.favorite.adapter.out.ExternalStockApiClient;
import com.newstoss.favorite.adapter.out.dto.ExternalStockApiResponse;
import com.newstoss.favorite.FavoriteStock.dto.FavoriteStockResponseDto;
import com.newstoss.global.errorcode.FavoriteErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.inbound.dto.response.v1.SearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteStockService {
    private final FavoriteStockRepository favoriteStockRepository;
    private final FavoriteRepository favoriteRepository;
    private final ExternalStockApiClient externalStockApiClient;

    public List<FavoriteStock> getFavoritesByMemberIdAndGroupId(UUID memberId, UUID groupId) {
        return favoriteStockRepository.findByMemberIdAndGroupIdOrderByStockSequenceAsc(memberId, groupId);
    }

    @Transactional
    public FavoriteStock addFavoriteStock(UUID memberId, UUID groupId, String stockCode) {
        log.info("Adding favorite stock - memberId: {}, groupId: {}, stockCode: {}", memberId, groupId, stockCode);
        
        try {
            // Favorite 그룹 조회 및 검증
            Favorite favorite = favoriteRepository.findById(groupId)
                    .orElseThrow(() -> {
                        log.error("Favorite group not found - groupId: {}", groupId);
                        return new CustomException(FavoriteErrorCode.FAVORITE_STOCK_NOT_FOUND);
                    });
            log.info("Found favorite group: {}", favorite.getGroupId());

            // memberId 검증
            if (!favorite.getMemberId().equals(memberId)) {
                log.error("Not authorized - memberId: {}, groupMemberId: {}", memberId, favorite.getMemberId());
                throw new CustomException(FavoriteErrorCode.FAVORITE_STOCK_NOT_FOUND);
            }
            log.info("Member ID verified");

            // 이미 추가된 주식인지 확인
            if (favoriteStockRepository.existsByFavoriteAndStockCode(favorite, stockCode)) {
                log.error("Stock already exists - stockCode: {}", stockCode);
                throw new CustomException(FavoriteErrorCode.ALREADY_EXISTS_FAVORITE_STOCK);
            }
            log.info("Stock not already in favorites");

            // 외부 API로 주식 존재 여부 확인
            try {
                ExternalStockApiResponse response = externalStockApiClient.getStockInfo(stockCode);
                if (!response.isSuccess()) {
                    log.warn("External API returned failure for stockCode: {}", stockCode);
                }
            } catch (Exception e) {
                log.error("External API error for stockCode: {}", stockCode, e);
                throw new CustomException(FavoriteErrorCode.EXTERNAL_API_ERROR);
            }
            log.info("Stock verified in external API: {}", stockCode);

            // 현재 그룹의 최대 sequence 값 조회
            Integer maxSequence = favoriteStockRepository.findMaxStockSequenceByFavorite(favorite);
            int nextSequence = (maxSequence == null) ? 1 : maxSequence + 1;

            // FavoriteStock 생성 및 저장
            FavoriteStock favoriteStock = FavoriteStock.createFavoriteStock(favorite, stockCode);
            favoriteStock.setStockSequence(nextSequence);
            FavoriteStock saved = favoriteStockRepository.save(favoriteStock);
            log.info("Successfully added favorite stock - favoriteStockId: {}, sequence: {}", saved.getFavoriteStockId(), saved.getStockSequence());
            return saved;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error adding favorite stock", e);
            throw new CustomException(FavoriteErrorCode.EXTERNAL_API_ERROR);
        }
    }

    @Transactional
    public void deleteFavoriteStock(UUID memberId, UUID groupId, String stockCode) {
        log.info("Deleting favorite stock - memberId: {}, groupId: {}, stockCode: {}", memberId, groupId, stockCode);
        
        try {
            // Favorite 그룹 조회 및 검증
            Favorite favorite = favoriteRepository.findById(groupId)
                    .orElseThrow(() -> {
                        log.error("Favorite group not found - groupId: {}", groupId);
                        return new RuntimeException("관심종목 그룹을 찾을 수 없습니다.");
                    });
            log.info("Found favorite group: {}", favorite.getGroupId());

            // memberId 검증
            if (!favorite.getMemberId().equals(memberId)) {
                log.error("Not authorized - memberId: {}, groupMemberId: {}", memberId, favorite.getMemberId());
                throw new RuntimeException("해당 그룹에 대한 권한이 없습니다.");
            }
            log.info("Member ID verified");

            // FavoriteStock 조회 및 삭제
            FavoriteStock favoriteStock = favoriteStockRepository.findByFavoriteAndStockCode(favorite, stockCode)
                    .orElseThrow(() -> {
                        log.error("Favorite stock not found - stockCode: {}", stockCode);
                        return new RuntimeException("관심종목에 등록되지 않은 주식입니다.");
                    });
            log.info("Found favorite stock: {}", favoriteStock.getFavoriteStockId());

            // 삭제할 sequence 값 저장
            Integer deletedSequence = favoriteStock.getStockSequence();

            // FavoriteStock 삭제
            favoriteStockRepository.delete(favoriteStock);
            log.info("Successfully deleted favorite stock - favoriteStockId: {}", favoriteStock.getFavoriteStockId());

            // 삭제된 sequence보다 큰 sequence를 가진 항목들의 sequence를 1씩 감소
            favoriteStockRepository.decrementStockSequenceAfter(favorite, deletedSequence);
            log.info("Successfully updated remaining stock sequences");
        } catch (Exception e) {
            log.error("Error deleting favorite stock", e);
            throw new RuntimeException("관심종목 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Transactional
    public FavoriteStock updateStockSequence(UUID memberId, UUID groupId, String stockCode, Integer newSequence) {
        log.info("Updating stock sequence - memberId: {}, groupId: {}, stockCode: {}, newSequence: {}", 
                memberId, groupId, stockCode, newSequence);
        
        try {
            // Favorite 그룹 조회 및 검증
            Favorite favorite = favoriteRepository.findById(groupId)
                    .orElseThrow(() -> {
                        log.error("Favorite group not found - groupId: {}", groupId);
                        return new RuntimeException("관심종목 그룹을 찾을 수 없습니다.");
                    });
            log.info("Found favorite group: {}", favorite.getGroupId());

            // memberId 검증
            if (!favorite.getMemberId().equals(memberId)) {
                log.error("Not authorized - memberId: {}, groupMemberId: {}", memberId, favorite.getMemberId());
                throw new RuntimeException("해당 그룹에 대한 권한이 없습니다.");
            }
            log.info("Member ID verified");

            // FavoriteStock 조회
            FavoriteStock favoriteStock = favoriteStockRepository.findByFavoriteAndStockCode(favorite, stockCode)
                    .orElseThrow(() -> {
                        log.error("Favorite stock not found - stockCode: {}", stockCode);
                        return new RuntimeException("관심종목에 등록되지 않은 주식입니다.");
                    });
            log.info("Found favorite stock: {}", favoriteStock.getFavoriteStockId());

            // 현재 sequence 값 저장
            Integer currentSequence = favoriteStock.getStockSequence();

            // 최대 sequence 값 조회
            Integer maxSequence = favoriteStockRepository.findMaxStockSequenceByFavorite(favorite);
            if (newSequence < 1 || newSequence > maxSequence) {
                throw new RuntimeException("유효하지 않은 순서입니다.");
            }

            // sequence 업데이트
            if (currentSequence < newSequence) {
                // 현재 순서보다 큰 순서로 이동하는 경우
                favoriteStockRepository.decrementStockSequenceBetween(favorite, currentSequence + 1, newSequence);
            } else if (currentSequence > newSequence) {
                // 현재 순서보다 작은 순서로 이동하는 경우
                favoriteStockRepository.incrementStockSequenceBetween(favorite, newSequence, currentSequence - 1);
            }

            // 대상 FavoriteStock의 sequence 업데이트
            favoriteStock.setStockSequence(newSequence);
            FavoriteStock updated = favoriteStockRepository.save(favoriteStock);
            log.info("Successfully updated stock sequence - favoriteStockId: {}, newSequence: {}", 
                    updated.getFavoriteStockId(), updated.getStockSequence());
            return updated;
        } catch (Exception e) {
            log.error("Error updating stock sequence", e);
            throw new RuntimeException("관심종목 순서 변경 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 외부 API 응답을 SearchResponseDto로 변환
     */
    private SearchResponseDto convertToSearchResponseDto(ExternalStockApiResponse externalResponse) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        
        // data 객체에서 정보를 가져옴
        ExternalStockApiResponse.StockData data = externalResponse.getData();
        
        searchResponseDto.setStockName(data.getStockName());
        searchResponseDto.setStockCode(data.getStockCode());
        searchResponseDto.setCurrentPrice(data.getPrice());  // price 필드 사용
        searchResponseDto.setSign(data.getSign());
        searchResponseDto.setChangeAmount(data.getChangeAmount());
        searchResponseDto.setChangeRate(data.getChangeRate());
        searchResponseDto.setStockImage(data.getStockImage());
        
        return searchResponseDto;
    }

    /**
     * 외부 API 호출 실패 시 사용할 기본 응답 생성
     */
    private SearchResponseDto createFallbackResponse(String stockCode) {
        SearchResponseDto fallbackResponse = new SearchResponseDto();
        fallbackResponse.setStockCode(stockCode);
        fallbackResponse.setStockName("정보 없음");
        fallbackResponse.setCurrentPrice("0");
        fallbackResponse.setSign("0");
        fallbackResponse.setChangeAmount("0");
        fallbackResponse.setChangeRate("0");
        fallbackResponse.setStockImage(null);
        return fallbackResponse;
    }

    /**
     * 관심 종목 정보를 외부 API로 조회하여 응답 DTO로 변환
     */
    public List<FavoriteStockResponseDto> getFavoriteStocksWithStockInfo(UUID memberId, UUID groupId) {
        List<FavoriteStock> favoriteStocks = getFavoritesByMemberIdAndGroupId(memberId, groupId);
        
        return favoriteStocks.stream()
                .map(favoriteStock -> {
                    try {
                        // 외부 API를 통해 실시간 주식 정보 조회
                        ExternalStockApiResponse stockInfo = externalStockApiClient.getStockInfo(favoriteStock.getStockCode());
                        
                        // ExternalStockApiResponse를 SearchResponseDto로 변환
                        SearchResponseDto searchResponseDto = convertToSearchResponseDto(stockInfo);
                        
                        return new FavoriteStockResponseDto(searchResponseDto, favoriteStock.getStockSequence());
                    } catch (Exception e) {
                        log.error("외부 API 호출 실패 - stockCode: {}, error: {}", favoriteStock.getStockCode(), e.getMessage());
                        
                        // 외부 API 호출 실패 시 기본 정보로 응답
                        SearchResponseDto fallbackResponse = createFallbackResponse(favoriteStock.getStockCode());
                        return new FavoriteStockResponseDto(fallbackResponse, favoriteStock.getStockSequence());
                    }
                })
                .toList();
    }
}
