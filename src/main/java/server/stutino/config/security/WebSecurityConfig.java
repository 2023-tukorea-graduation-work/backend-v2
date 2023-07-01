package server.stutino.config.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import server.stutino.auth.JwtTokenizer;
import server.stutino.auth.filter.JwtAuthenticationFilter;
import server.stutino.auth.filter.JwtLogoutFilter;
import server.stutino.auth.filter.JwtReissueFilter;
import server.stutino.auth.filter.JwtVerificationFilter;
import server.stutino.auth.handler.UserAccessDeniedHandler;
import server.stutino.auth.handler.UserAuthenticationEntryPoint;
import server.stutino.auth.handler.UserAuthenticationFailureHandler;
import server.stutino.auth.handler.UserAuthenticationSuccessHandler;
import server.stutino.auth.service.RedisService;
import server.stutino.auth.userdetails.CustomUserDetailsService;
import server.stutino.auth.utils.CustomAuthorityUtils;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {
    JwtTokenizer jwtTokenizer;
    CustomAuthorityUtils customAuthorityUtils;
    RedisService redisService;
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        // cors 설정
        http.cors().configurationSource(corsConfigurationSource());
        // 세션 사용 x
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable()
                .httpBasic().disable() // http basic 인증 방식 사용 x
                .formLogin().disable() // form login 인증 방식 사용 x
                .logout().disable();
        // jwt 인증 필터 적용
        http.apply(new CustomFilterConfigurer());
        // jwt 검증 실패 예외처리 및 권한 예외처리
        http.exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint())
                .accessDeniedHandler(new UserAccessDeniedHandler());
        http.authorizeRequests()
                // load balancing target group health check
                .antMatchers(HttpMethod.POST, "/auth/logout","auth/reissue").authenticated()
                /*
                * todo: 1. 로그인 없이도 접근 가능한 요청 조회 목록 생각하기
                *       2. 역할 별 접근 권한 설정은 어떻게 하는가?
                * */
                .anyRequest().permitAll();
//                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 모든 origin 허용
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return urlBasedCorsConfigurationSource;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, redisService);
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

            JwtVerificationFilter jwtVerificationFilter =
                    new JwtVerificationFilter(jwtTokenizer, customAuthorityUtils,redisService);

            JwtReissueFilter jwtReissueFilter = new JwtReissueFilter(redisService, jwtTokenizer, customUserDetailsService);

            JwtLogoutFilter jwtLogoutFilter = new JwtLogoutFilter(jwtTokenizer, redisService);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class)
                    .addFilterAfter(jwtReissueFilter, JwtVerificationFilter.class)
                    .addFilterAfter(jwtLogoutFilter, JwtVerificationFilter.class);
        }
    }
}
