package com.newstoss.member.application.in.command;

import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.application.out.MemberQueryPort;
import com.newstoss.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class InvestScoreService {
    private final MemberQueryPort memberQueryPort;
    public void exec(UUID memberId, Long invest_score){
        Member member = memberQueryPort.findById(memberId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        member.changeInvestScore(invest_score);
    }
}
