package com.newstoss.member.adapter.out.persistence;

import com.newstoss.member.adapter.in.web.dto.response.MemberInfoDto;
import com.newstoss.member.adapter.in.web.dto.response.MemberStockDto;
import com.newstoss.member.adapter.in.web.dto.response.QMemberInfoDto;

import com.newstoss.member.adapter.in.web.dto.response.QMemberStockDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

import static com.newstoss.member.domain.QMember.*;
import static com.newstoss.portfolio.entity.QPortfolio.*;
import static com.newstoss.portfolio.entity.QPortfolioStock.*;

@Repository
@RequiredArgsConstructor
public class JpaMemberInfoRepositoryImpl implements JpaMemberInfoRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberInfoDto findMemberInfo(UUID id) {
        MemberInfoDto dto = new MemberInfoDto();
        String name = queryFactory
                .select(member.name)
                .from(member)
                .fetchOne();

        Long asset = queryFactory
                .select(portfolio.asset)
                .from(portfolio)
                .fetchOne();

        List<MemberStockDto> dtos = queryFactory
                .select(new QMemberStockDto(portfolioStock.stock.stockCode, portfolioStock.stock.stockCode ,
                        portfolioStock.unrealizedPnl))
                .from(portfolioStock)
                .fetch();
        long pnl = 0L;
        for (MemberStockDto memberStockDto : dtos) {
            pnl += memberStockDto.getPnl();
        }
        dto.setUsername(name);
        dto.setUserPnl(pnl);
        dto.setAsset(asset);
        dto.setMemberStocks(dtos);
        return dto;
    }
}
