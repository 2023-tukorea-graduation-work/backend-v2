package server.stutino.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import server.stutino.auth.JwtTokenizer;
import server.stutino.auth.service.RedisService;
import server.stutino.auth.utils.ErrorResponder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtLogoutFilter extends OncePerRequestFilter {

    JwtTokenizer jwtTokenizer;
    RedisService redisService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        String refreshToken = request.getHeader(JwtTokenizer.REFRESH_TOKEN_HEADER);

        return !request.getMethod().equals("POST")
                || !requestURI.equals("/auth/logout")
                || !StringUtils.hasText(refreshToken);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // request로 부터 access token과 refresh token을 받는다.
        // redis에서 refresh token을 key로 가지는 데이터가 있는지 확인한다.
        // 해당 키-값 쌍 데이터를 제거한다.
        // access token 유효성 검증은 앞의 verification filter에서 진행
        // 로그아웃 된 토큰 검사도 verification filter에서 진행

        try {
            String accessToken = resolveAccessToken(request, response);
            String refreshToken = resolveRefreshToken(request, response);
            // access token payload 뽑기
            Jws<Claims> claims =
                    jwtTokenizer.getClaims(accessToken,
                            jwtTokenizer.encodeSecretKeyWithBase64(jwtTokenizer.getSecretKey()));
            redisService.deleteRefreshToken(refreshToken);

            long remainExpiration = calculateRemainExpiration(claims);
            // access token 값을 키로 logout 문자열을 값으로 하는 데이터 레디스에 저장, 만료 시간 명시
            redisService.setAccessTokenLogout(accessToken, remainExpiration);


            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("로그아웃 성공");
        } catch (Exception e) {
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    // access token 뽑기
    private String resolveAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bearerToken = request.getHeader(JwtTokenizer.ACCESS_TOKEN_HEADER);
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(JwtTokenizer.TOKEN_PREFIX)) {
            log.info("Header hasn't contain access token, Authorization: {}", bearerToken);
            ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Header hasn't contain access token");
        }
        return bearerToken.replace(JwtTokenizer.TOKEN_PREFIX, "");
    }

    // refresh token 뽑기
    private String resolveRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(JwtTokenizer.REFRESH_TOKEN_HEADER);
        if (!StringUtils.hasText(refreshToken)) {
            log.info("Header hasn't contain refresh token, Refresh: {}", refreshToken);
            ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Header hasn't contain refresh token");
        }
        return refreshToken;
    }

    // 만료시간 계산
    private long calculateRemainExpiration(Jws<Claims> claims) {
        return claims.getBody().getExpiration().getTime() - new Date().getTime();
    }
}