package tuk.mentor.config.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tuk.mentor.auth.filter.JwtTokenizer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {
    JwtTokenizer jwtTokenizer;


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                requests ->
                            requests.antMatchers("/login/**")
                                    .permitAll()
                                    .antMatchers(HttpMethod.POST, "/mentor")
                                    .permitAll()
                                    .antMatchers(HttpMethod.POST, "/mentee")
                                    .permitAll()
                                    .antMatchers("/mentor/**")
                                    .hasRole("MENTOR")
                                    .antMatchers("/mentee/**")
                                    .hasRole("MENTEE")
                                    .antMatchers("/admin/**")
                                    .hasRole("ADMIN")
                                    .anyRequest()
                                    .authenticated()
                )
                .csrf()
                .disable()
                .cors();

        return http.build();
    }
    /**
     * Cors 설정
     *
     * @author Hyeonjun Park
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
