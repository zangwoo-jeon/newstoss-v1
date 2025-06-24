package com.newstoss.member.adapter.out.persistence;

import com.newstoss.member.adapter.in.web.dto.response.MemberInfoDto;
import com.newstoss.member.adapter.in.web.dto.response.MemberStockDto;
import com.newstoss.member.adapter.in.web.dto.response.QMemberInfoDto;

import com.newstoss.member.adapter.in.web.dto.response.QMemberStockDto;
import com.newstoss.portfolio.entity.QMemberPnl;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.newstoss.member.domain.QMember.*;
import static com.newstoss.portfolio.entity.QMemberPnl.memberPnl;
import static com.newstoss.portfolio.entity.QPortfolio.*;
import static com.newstoss.portfolio.entity.QPortfolioStock.*;

@Repository
@RequiredArgsConstructor
public class JpaMemberInfoRepositoryImpl implements JpaMemberInfoRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberInfoDto findMemberInfo(UUID id) {
        Tuple result = queryFactory
                .select(member.name, member.investScore , memberPnl.asset)
                .from(member)
                .join(memberPnl).on(memberPnl.memberId.eq(member.memberId))
                .where(member.memberId.eq(id), memberPnl.date.eq(LocalDate.now()))
                .fetchOne();

        if (result == null) return null;

        return getData(result, id);
    }

    @Override
    public List<MemberInfoDto> findMemberInfos() {
        List<MemberInfoDto> dtos = new ArrayList<>();
        List<Tuple> result = queryFactory
                .select(member.memberId,member.name, member.investScore, memberPnl.asset)
                .from(member)
                .join(memberPnl).on(memberPnl.memberId.eq(member.memberId))
                .where(memberPnl.date.eq(LocalDate.now()))
                .fetch();
        if (result.isEmpty()) return dtos;
        for (Tuple tuple : result) {
            UUID memberId = tuple.get(member.memberId);
            MemberInfoDto data = getData(tuple, memberId);
            dtos.add(data);
        }
        return dtos;
    }

    private MemberInfoDto getData(Tuple tuple, UUID memberId) {
        MemberInfoDto dto = new MemberInfoDto();
        dto.setMemberId(memberId);
        dto.setUsername(tuple.get(member.name));
        dto.setAsset(tuple.get(memberPnl.asset));
        dto.setInvestScore(tuple.get(member.investScore));

        List<MemberStockDto> list = queryFactory
                .select(new QMemberStockDto(portfolioStock.stock.stockCode, portfolioStock.stock.name ,
                        portfolioStock.unrealizedPnl))
                .from(portfolioStock)
                .where(portfolioStock.memberId.eq(memberId))
                .fetch();
        long pnl = 0L;
        for (MemberStockDto memberStockDto : list) {
            pnl += memberStockDto.getPnl();
        }
        dto.setUserPnl(pnl);
        dto.setMemberStocks(list);
        return dto;
    }
}
