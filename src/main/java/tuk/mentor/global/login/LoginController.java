package tuk.mentor.global.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuk.mentor.global.exception.BadCredentialsException;

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
