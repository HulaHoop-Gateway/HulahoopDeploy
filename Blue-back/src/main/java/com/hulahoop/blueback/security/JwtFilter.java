package com.hulahoop.blueback.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // 공개 허용 경로 목록
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/login",
            "/api/member/signup",
            "/api/member/check-id",
            "/api/member/check-email",
            "/api/member/check-phone",
            "/api/member/find-id",
            "/api/member/reset-password",
            "/api/payments",
            "/api/payments/create",
            "/api/payments/confirm",
            "/api/ai/reset" // SecurityConfig에 추가된 경로도 포함
    );

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 필터가 실행되어야 하는지 여부를 결정합니다.
     * true를 반환하면 doFilterInternal이 실행되지 않고, 다음 필터로 즉시 넘어갑니다.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // 1. OPTIONS 요청은 항상 필터를 건너뜁니다. (CORS 사전 요청)
        if ("OPTIONS".equalsIgnoreCase(method)) {
            System.out.println("✅ [JwtFilter] OPTIONS 요청 - 필터 건너뜀.");
            return true;
        }

        // 2. 공개 경로 목록에 포함된 경로인지 확인합니다.
        if (isPublicPath(path)) {
            System.out.println("✅ [JwtFilter] 공개 경로 (" + path + ") - 필터 건너뜀.");
            return true;
        }

        return false;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // shouldNotFilter가 true인 경우 이 메서드는 호출되지 않으며,
        // 인증이 필요한 요청에 대해서만 아래 로직이 실행됩니다.
        String path = request.getRequestURI();

        System.out.println("🔍 [JwtFilter] 인증 요청 경로: " + path + " | 메소드: " + request.getMethod());


        // 1) JWT Authorization 헤더 검증
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.err.println("❌ [JwtFilter] Authorization 헤더 없음 또는 잘못된 형식: " + path);
            sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "MISSING_TOKEN",
                    "로그인이 필요합니다. Authorization 헤더가 없거나 형식이 잘못되었습니다.");
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("🔑 [JwtFilter] JWT 토큰 검증 시작...");

        // 토큰 검증
        if (!jwtUtil.validateToken(token)) {
            String errorType = jwtUtil.getValidationError(token);
            System.err.println("❌ [JwtFilter] JWT 검증 실패 - 원인: " + errorType);

            String errorMessage = switch (errorType) {
                case "TOKEN_EXPIRED" -> "토큰이 만료되었습니다. 다시 로그인해주세요.";
                case "TOKEN_MALFORMED" -> "토큰 형식이 올바르지 않습니다.";
                case "TOKEN_INVALID_SIGNATURE" -> "토큰 서명이 유효하지 않습니다.";
                case "TOKEN_EMPTY" -> "토큰이 비어있습니다.";
                default -> "유효하지 않은 토큰입니다.";
            };

            sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, errorType, errorMessage);
            return;
        }

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.extractUsername(token);
        System.out.println("✅ [JwtFilter] JWT 검증 성공 - 사용자: " + username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4) 다음 필터로 진행
        filterChain.doFilter(request, response);
    }

    // 공개 허용 경로 체크 (shouldNotFilter에서 사용)
    private boolean isPublicPath(String path) {
        if (path == null)
            return false;

        String tempPath = path; // 임시 변수 생성

        // URL 끝에 '/'가 있으면 제거 (임시 변수 수정)
        if (tempPath.endsWith("/")) {
            tempPath = tempPath.substring(0, tempPath.length() - 1);
        }

        // 람다에서 참조될 최종 정규화된 경로를 final 변수로 선언하여 람다 규칙을 준수합니다.
        final String normalizedPath = tempPath;

        // PUBLIC_PATHS와 정확히 일치하거나 (예: /api/login)
        // PUBLIC_PATHS의 접두사로 시작하는지 확인 (예: /api/payments 로 /api/payments/create 처리)
        return PUBLIC_PATHS.stream().anyMatch(publicPath -> {
            // 정확히 일치하는 경우
            if (publicPath.equals(normalizedPath)) { // normalizedPath 사용
                return true;
            }
            // 하위 경로인 경우 (예: publicPath="/api/payments", normalizedPath="/api/payments/create")
            return normalizedPath.startsWith(publicPath + "/"); // normalizedPath 사용
        });
    }

    // JSON 형식 에러 응답
    private void sendJsonError(HttpServletResponse response, int status, String errorType, String message)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = String.format(
                "{\"error\": \"%s\", \"message\": \"%s\", \"status\": %d}",
                errorType, message, status);

        response.getWriter().write(jsonResponse);
    }
}