package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAMemberPnlRepository;
import com.newstoss.portfolio.application.port.out.CreateMemberPnlPort;
import com.newstoss.portfolio.application.port.out.DeleteMemberPnlPort;
import com.newstoss.portfolio.entity.MemberPnl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberPnlCommandAdapter implements DeleteMemberPnlPort, CreateMemberPnlPort {

    private final JPAMemberPnlRepository repository;

    @Override
    public Long create(MemberPnl memberPnl) {
        MemberPnl saved = repository.save(memberPnl);
        return saved.getId();
    }

    @Override
    public void deleteMemberPnl(UUID memberId) {
        List<MemberPnl> memberPnls = repository.findByMemberId(memberId);
        repository.deleteAll(memberPnls);
    }

}
