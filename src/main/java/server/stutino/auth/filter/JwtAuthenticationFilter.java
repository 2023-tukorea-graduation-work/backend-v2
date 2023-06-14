package server.stutino.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;
import server.stutino.auth.JwtTokenizer;
import server.stutino.auth.dto.LoginRequest;
import server.stutino.auth.service.RedisService;
import server.stutino.auth.userdetails.CustomUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    AuthenticationManager authenticationManager;
    JwtTokenizer jwtTokenizer;
    RedisService redisService;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        LoginRequest loginRequest = objectMapper.readValue(messageBody, LoginRequest.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        // authentication 객체가 security context 에 저장됨
        return authenticationManager.authenticate(authenticationToken);
    }

    /*인증 성공시 실행*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();

        String base64EncodedSecretKey = jwtTokenizer.encodeSecretKeyWithBase64(jwtTokenizer.getSecretKey());
        String accessToken = delegateAccessToken(user, base64EncodedSecretKey); // access token 생성
        String refreshToken = delegateRefreshToken(user, base64EncodedSecretKey); // refresh token 생성

        response.addHeader(JwtTokenizer.ACCESS_TOKEN_HEADER, JwtTokenizer.TOKEN_PREFIX + accessToken);
        response.addHeader(JwtTokenizer.REFRESH_TOKEN_HEADER, refreshToken);

        /*현재 refresh token을 키로 하는 데이터가 없으면 refresh token 레디스에 저장*/
        if (redisService.getRefreshToken(refreshToken) == null) {
            redisService.setRefreshToken(refreshToken, user.getEmail(), jwtTokenizer.getRefreshTokenExpirationMinutes());
        }

        this.getSuccessHandler().onAuthenticationSuccess(request,response,authResult);
    }

    private String delegateAccessToken(CustomUserDetails user, String base64EncodedSecretKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getMemberId());
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRoles());

        String subject = user.getEmail();
        Date tokenExpiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, tokenExpiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(CustomUserDetails user, String base64EncodedSecretKey) {
        String subject = user.getEmail();
        Date tokenExpiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, tokenExpiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
