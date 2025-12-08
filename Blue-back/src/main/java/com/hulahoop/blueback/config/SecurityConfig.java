package com.hulahoop.blueback.config;

import com.hulahoop.blueback.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ✅ CORS 설정 적용 (Security 필터 체인에서 처리)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 인가 규칙
                .authorizeHttpRequests(auth -> auth
                        // ✅ preflight 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ GET 중복체크 허용
                        .requestMatchers(HttpMethod.GET,
                                "/api/member/check-id",
                                "/api/member/check-email",
                                "/api/member/check-phone")
                        .permitAll()

                        // ✅ 로그인/회원가입/찾기/리셋/결제/AI 리셋 비회원 허용
                        .requestMatchers(
                                "/api/login",
                                "/api/member/signup",
                                "/api/member/find-id",
                                "/api/member/reset-password",
                                "/api/payments/**",
                                "/api/ai/reset")
                        .permitAll()

                        // ✅ 나머지는 JWT 필요
                        .anyRequest().authenticated())

                .formLogin(form -> form.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ CORS 설정 Bean 추가
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();

        configuration.setAllowedOriginPatterns(java.util.Arrays.asList(
                "http://localhost:3000",
                "http://localhost:3001",
                "http://localhost:5173",
                "http://43.201.205.26:3000",
                "http://43.201.205.26:5173",
                "http://hulahoop.ai.kr",
                "http://www.hulahoop.ai.kr",
                "http://admin.hulahoop.ai.kr",
                "http://cinema.hulahoop.ai.kr",
                "http://bikeway.hulahoop.ai.kr",
                // HTTPS 도메인 추가
                "https://hulahoop.ai.kr",
                "https://www.hulahoop.ai.kr",
                "https://admin.hulahoop.ai.kr",
                "https://cinema.hulahoop.ai.kr",
                "https://bikeway.hulahoop.ai.kr"));
        configuration.setAllowedMethods(java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(java.util.Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(java.util.Arrays.asList("Authorization", "Set-Cookie"));

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
