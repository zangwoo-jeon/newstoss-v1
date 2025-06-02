package com.newstoss.member.application.in.query;

import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.domain.Member;
import com.newstoss.member.application.out.MemberQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMemberService {
    private final MemberQueryPort memberQueryPort;

    public Member findByAccount(String account) {
        return memberQueryPort.findByAccount(account)
                .orElseThrow(() -> new CustomException(UserErrorCode.ACCOUNT_NOT_FOUND));
    }

    public Member findById(UUID memberId){
        return memberQueryPort.findById(memberId)
                .orElseThrow(() -> new CustomException(UserErrorCode.MEMBERID_NOT_FOUND));
    }

}
