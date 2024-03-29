package server.stutino.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Getter
@Component
@PropertySource("classpath:env.yml")
public class JwtTokenizer {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh";

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    @Value("${JWT_ACCESS_TOKEN_EXPIRATION_MINUTES}")
    private int accessTokenExpirationMinutes;

    @Value("${JWT_REFRESH_TOKEN_EXPIRATION_MINUTES}")
    private int refreshTokenExpirationMinutes;

    /* secret key의 byte array를 base64 인코딩, Key가 항상 바이너리이기 때문에
    jwt에서 plain text를 키로 사용하는 것은 권장하지 않음*/
    public String encodeSecretKeyWithBase64(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /*access token 생성*/
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedKey) {

        Key key = getKeyFromBase64EncodedKey(base64EncodedKey);

        return Jwts.builder()
                .setClaims(claims) // payload(키/값 쌍 형태)
                .setSubject(subject) // payload sub 값
                .setIssuedAt(Calendar.getInstance().getTime()) // 발행 시간(Date 타입)
                .setExpiration(expiration) // 만료 시간
                .signWith(key) // 서명에 사용한 키
                .compact(); // 빌드
    }

    /*refresh token 생성*/
    public String generateRefreshToken(String subject,
                                       Date expiration,
                                       String base64EncodedSecretKey) {

        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    /*jwt 검증 후 claims 반환*/
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(jws);
    }

    /*토큰 만료 시간 생성*/
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    /*JWT 서명에 사용할 key 생성*/
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] decodedSecretKey = Decoders.BASE64.decode(base64EncodedSecretKey);
        SecretKey key = Keys.hmacShaKeyFor(decodedSecretKey);
        return key;
    }

    // jwt 유효성 검증
    public boolean validateToken(String jwt) {
        return this.getClaims(jwt, encodeSecretKeyWithBase64(secretKey)) != null;
    }
}