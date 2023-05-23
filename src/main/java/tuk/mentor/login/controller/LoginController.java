package tuk.mentor.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.exception.BadCredentialsException;
import tuk.mentor.login.LoginInfo;
import tuk.mentor.login.service.LoginService;
import tuk.mentor.login.dto.LoginRequest;
import tuk.mentor.login.dto.LoginResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(HttpServletRequest httpServletRequest, @RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.login(loginRequest);
        if(response != null) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(
                    "loginInfo",
                    LoginInfo.builder()
                        .userID(response.getId())
                            .role(response.getRole())
                            .build()
            );

            return ResponseEntity.ok().body(response);
        }
        else throw new BadCredentialsException();
    }

    @GetMapping
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        return ResponseEntity.ok().build();
    }
}
