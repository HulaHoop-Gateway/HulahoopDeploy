package com.hulahoop.redback.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminJwtFilter extends OncePerRequestFilter {

    private final AdminJwtUtil adminJwtUtil;

    public AdminJwtFilter(AdminJwtUtil adminJwtUtil) {
        this.adminJwtUtil = adminJwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        System.out.println("[AdminJwtFilter] 요청: " + method + " " + path);

        // 관리자 로그인은 JWT 필요 없음
        if (isAdminPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // /api/admin/** 요청만 JWT 인증 필요
        if (!path.startsWith("/api/admin")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 검사
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Missing Authorization Header");
            return;
        }

        String token = authHeader.substring(7);

        if (!adminJwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Invalid or Expired Token");
            return;
        }

        String adminId = adminJwtUtil.extractAdminId(token);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(adminId, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private boolean isAdminPublicPath(String path) {
        return path.startsWith("/api/admin/login");
    }
}
