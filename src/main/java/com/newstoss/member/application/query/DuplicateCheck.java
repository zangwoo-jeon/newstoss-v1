package com.newstoss.member.application.query;

import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.domain.Member;
import com.newstoss.member.domain.MemberQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DuplicateCheck {
    private final MemberQueryPort memberQueryPort;

    public boolean exec(String account){
        Optional<Member> existing = memberQueryPort.findByAccount(account);
        if (existing.isPresent()) {
            throw new CustomException(UserErrorCode.DUPLICATE_ACCOUNT);
        }
        return true;
    }

}
