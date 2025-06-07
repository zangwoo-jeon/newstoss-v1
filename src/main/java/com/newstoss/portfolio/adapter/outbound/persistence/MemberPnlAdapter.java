package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.global.errorcode.MemberPnlErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAMemberPnlRepository;
import com.newstoss.portfolio.application.port.out.CreateMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPeriodPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.application.port.out.UpdateMemberPnlPort;
import com.newstoss.portfolio.entity.MemberPnl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberPnlAdapter implements GetMemberPnlPeriodPort , GetMemberPnlPort , CreateMemberPnlPort , UpdateMemberPnlPort {

    private final JPAMemberPnlRepository repository;

    @Override
    public List<MemberPnl> getMemberPnlDaily(UUID memberId, LocalDate start, LocalDate end) {
        List<MemberPnl> byMemberIdAndDateBetween = repository.findByMemberIdAndDateBetween(memberId, start, end);
        if (byMemberIdAndDateBetween.isEmpty()) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND);
        }
        return byMemberIdAndDateBetween;
    }

    @Override
    public Optional<MemberPnl> getMemberPnl(UUID memberId, LocalDate date) {
        return repository.findByMemberIdAndDate(memberId, date);
    }

    @Override
    public Long getMemberPnlAcc(UUID memberId, LocalDate start, LocalDate end) {
        Long acc = repository.findAccByMemberIdAndDateBetween(memberId, start, end);
        if (acc == null) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND);
        }
        return acc;
    }

    @Override
    public Long getMemberPnlAcc(UUID memberId) {
        Long accByMemberId = repository.findAccByMemberId(memberId);
        if (accByMemberId == null) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND);
        }
        return accByMemberId;
    }

    @Override
    public Long create(MemberPnl memberPnl) {
        MemberPnl saved = repository.save(memberPnl);
        return saved.getId();
    }

    @Override
    public void updateMemberPnl(UUID memberId, Integer pnl, Long asset) {
        MemberPnl memberPnl = repository.findByMemberIdAndDate(memberId, LocalDate.now())
                .orElseThrow(() -> new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND));
        memberPnl.updatePnl(pnl, asset);
    }
}
