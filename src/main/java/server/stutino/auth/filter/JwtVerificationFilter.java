package server.stutino.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import server.stutino.auth.JwtTokenizer;
import server.stutino.auth.service.RedisService;
import server.stutino.auth.userdetails.CustomUserDetails;
import server.stutino.auth.utils.CustomAuthorityUtils;
import server.stutino.domain.member.Member;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtVerificationFilter extends OncePerRequestFilter {
    JwtTokenizer jwtTokenizer;
    CustomAuthorityUtils customAuthorityUtils;
    RedisService redisService;

    /*access 토큰 검증 성공 시 다음 필터 진행*/
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (ExpiredJwtException jwtException) {
            request.setAttribute("exception", jwtException);
        }
        filterChain.doFilter(request, response);
    }

    /*access token 이 포함되어 있지 않으면 필터를 타지 않음*/
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader(JwtTokenizer.ACCESS_TOKEN_HEADER);

        return authorization == null || !authorization.startsWith(JwtTokenizer.TOKEN_PREFIX);
    }

    /*토큰 검증 후 claims 반환*/
    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader(JwtTokenizer.ACCESS_TOKEN_HEADER).replace(JwtTokenizer.TOKEN_PREFIX, "");

        if (StringUtils.hasText(redisService.getAccessToken(jws))) {
            throw new UnsupportedJwtException("로그아웃 된 토큰");
        }
        // secret key 로 지정한 문자열 base64로 인코딩
        String base64EncodedSecretKey = jwtTokenizer.encodeSecretKeyWithBase64(jwtTokenizer.getSecretKey());

        // base64 인코딩 된 secret key 를 jws 와 함께 getClaims()에 넘기면
        // secret key 를 jwt 서명에 사용한 키로 바꿔주고 검증 한 후에 claims 를 꺼내온다.
        return jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
    }

    /*시큐리티 세션에 접근해서 Authentication 객체 저장*/
    private void setAuthenticationToContext(Map<String, Object> claims) {

        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = customAuthorityUtils.createAuthorities(roles);

        Member member = Member.builder()
                .id(Long.parseLong(String.valueOf(claims.get("memberId"))))
                .email((String) claims.get("email"))
                .roles(roles)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(customAuthorityUtils, member);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
