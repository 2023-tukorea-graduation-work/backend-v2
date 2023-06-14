package server.stutino.auth.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import server.stutino.auth.dto.LoginUser;
import server.stutino.domain.member.entity.Member;

@Service
public class AuthService {
    public Member getLoginUser() {
        return ((LoginUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .getMember();
    }
}