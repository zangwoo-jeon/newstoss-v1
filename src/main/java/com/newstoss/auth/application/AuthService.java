package com.newstoss.auth.application;

import com.newstoss.auth.adapter.in.web.dto.requestDTO.LoginDTO;
import com.newstoss.auth.application.command.LoginService;
import com.newstoss.auth.application.command.RefreshService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    //로그읜
    //로그아웃
    private final LoginService loginService;
    private final RefreshService refreshService;

    public String login(LoginDTO loginDTO){
        return loginService.exec(loginDTO);
    }
    public String token(HttpServletRequest request){return refreshService.exec(request);}


}
