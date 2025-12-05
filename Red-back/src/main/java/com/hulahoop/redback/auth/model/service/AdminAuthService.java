package com.hulahoop.redback.auth.model.service;

import com.hulahoop.redback.auth.model.dao.AuthMapper;
import com.hulahoop.redback.auth.model.dto.AuthDTO;
import com.hulahoop.redback.security.AdminJwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminAuthService {

    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder encoder;
    private final AdminJwtUtil adminJwtUtil;

    public AdminAuthService(AuthMapper authMapper,
            BCryptPasswordEncoder encoder,
            AdminJwtUtil adminJwtUtil) {
        this.authMapper = authMapper;
        this.encoder = encoder;
        this.adminJwtUtil = adminJwtUtil;
    }

    public Map<String, String> login(String id, String password) {

        // 1️⃣ 관리자 조회
        AuthDTO admin = authMapper.findAdminById(id);

        if (admin == null) {
            throw new RuntimeException("관리자 계정 또는 비밀번호 오류");
        }

        // 2️⃣ 비밀번호 검증(BCrypt)
        if (!encoder.matches(password, admin.getPassword())) {
            throw new RuntimeException("관리자 계정 또는 비밀번호 오류");
        }

        // 3️⃣ JWT 발급 및 관리자 정보 반환
        String token = adminJwtUtil.generateToken(admin.getId());

        return Map.of(
                "token", token,
                "adminName", admin.getName());
    }
}
