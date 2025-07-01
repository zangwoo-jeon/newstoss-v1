package com.newstoss.member.application.in.command;

import com.newstoss.global.handler.CustomException;
import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.member.domain.Member;
import com.newstoss.member.application.out.MemberCommandPort;
import com.newstoss.member.application.out.MemberQueryPort;
import com.newstoss.portfolio.application.port.out.DeleteMemberPnlPort;
import com.newstoss.portfolio.application.port.out.DeleteMemberPortfolioPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final MemberCommandPort memberCommandPort;
    private final MemberQueryPort memberQueryPort;
    private final DeleteMemberPnlPort deleteMemberPnlPort;
    private final DeleteMemberPortfolioPort deleteMemberPortfolioPort;
    private final ApplicationEventPublisher publisher;

    public boolean exec(UUID memberId){
        Optional<Member> member = memberQueryPort.findById(memberId);
        if (member.isEmpty()){
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }
        memberCommandPort.deleteById(memberId);
        publisher.publishEvent(memberId);
        return true;
    }
}
