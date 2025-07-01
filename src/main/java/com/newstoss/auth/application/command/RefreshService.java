package com.newstoss.auth.application.command;

import com.newstoss.global.errorcode.JwtErrorCode;
import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.jwt.JwtProvider;
import com.newstoss.global.jwt.JwtResolver;
import com.newstoss.member.application.in.query.GetMemberInfoUseCase;
import com.newstoss.member.application.out.MemberQueryPort;
import com.newstoss.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshService {

    private final JwtProvider jwtProvider;
    private final JwtResolver jwtResolver;
    private final MemberQueryPort memberQueryPort;

    public String exec(HttpServletRequest request){
        if (request == null) {
            throw new CustomException(JwtErrorCode.MISSING_TOKEN);
        }else {
            UUID memberId = jwtResolver.extractMemberId(request);
            if (memberId ==null){
                throw new CustomException(JwtErrorCode.INVALID_TOKEN);
            }
            Member member = memberQueryPort.findById(memberId)
                    .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
            return jwtProvider.generateToken(member);
        }
    }
}
