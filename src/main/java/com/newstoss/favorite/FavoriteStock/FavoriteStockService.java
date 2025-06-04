package com.newstoss.favorite.FavoriteStock;

import com.newstoss.favorite.FavoriteGroup.Favorite;
import com.newstoss.favorite.FavoriteGroup.FavoriteRepository;
import com.newstoss.stock.entity.Stock;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
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
    private final StockRepository stockRepository;

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
                        return new RuntimeException("관심종목 그룹을 찾을 수 없습니다.");
                    });
            log.info("Found favorite group: {}", favorite.getGroupId());

            // memberId 검증
            if (!favorite.getMemberId().equals(memberId)) {
                log.error("Not authorized - memberId: {}, groupMemberId: {}", memberId, favorite.getMemberId());
                throw new RuntimeException("해당 그룹에 대한 권한이 없습니다.");
            }
            log.info("Member ID verified");

            // Stock 조회 및 검증
            Stock stock = stockRepository.findByStockCode(stockCode)
                    .orElseThrow(() -> {
                        log.error("Stock not found - stockCode: {}", stockCode);
                        return new RuntimeException("주식을 찾을 수 없습니다.");
                    });
            log.info("Found stock: {}", stock.getStockCode());

            // 이미 추가된 주식인지 확인
            if (favoriteStockRepository.existsByFavoriteAndStock(favorite, stock)) {
                log.error("Stock already exists - stockCode: {}", stockCode);
                throw new RuntimeException("이미 추가된 주식입니다.");
            }
            log.info("Stock not already in favorites");

            // 현재 그룹의 최대 sequence 값 조회
            Integer maxSequence = favoriteStockRepository.findMaxStockSequenceByFavorite(favorite);
            int nextSequence = (maxSequence == null) ? 1 : maxSequence + 1;

            // FavoriteStock 생성 및 저장
            FavoriteStock favoriteStock = FavoriteStock.createFavoriteStock(favorite, stock);
            favoriteStock.setStockSequence(nextSequence);
            FavoriteStock saved = favoriteStockRepository.save(favoriteStock);
            log.info("Successfully added favorite stock - favoriteStockId: {}, sequence: {}", saved.getFavoriteStockId(), saved.getStockSequence());
            return saved;
        } catch (Exception e) {
            log.error("Error adding favorite stock", e);
            throw new RuntimeException("관심종목 추가 중 오류가 발생했습니다: " + e.getMessage());
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

            // Stock 조회 및 검증
            Stock stock = stockRepository.findByStockCode(stockCode)
                    .orElseThrow(() -> {
                        log.error("Stock not found - stockCode: {}", stockCode);
                        return new RuntimeException("주식을 찾을 수 없습니다.");
                    });
            log.info("Found stock: {}", stock.getStockCode());

            // FavoriteStock 조회 및 삭제
            FavoriteStock favoriteStock = favoriteStockRepository.findByFavoriteAndStock(favorite, stock)
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

            // Stock 조회 및 검증
            Stock stock = stockRepository.findByStockCode(stockCode)
                    .orElseThrow(() -> {
                        log.error("Stock not found - stockCode: {}", stockCode);
                        return new RuntimeException("주식을 찾을 수 없습니다.");
                    });
            log.info("Found stock: {}", stock.getStockCode());

            // FavoriteStock 조회
            FavoriteStock favoriteStock = favoriteStockRepository.findByFavoriteAndStock(favorite, stock)
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
}
