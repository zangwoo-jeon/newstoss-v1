package com.newstoss.member.application.in.command;

import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.domain.Member;
import com.newstoss.member.application.out.MemberCommandPort;
import com.newstoss.member.application.out.MemberQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FgOffsetService {
    private final MemberQueryPort memberQueryPort;
    private final MemberCommandPort memberCommandPort;

    public void exec(UUID memberId, UUID fgOffset){
        Member member = memberQueryPort.findById(memberId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        member.changeFgOffset(fgOffset);
    }
}
