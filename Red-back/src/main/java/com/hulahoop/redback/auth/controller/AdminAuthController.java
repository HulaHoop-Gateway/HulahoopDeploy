package com.hulahoop.redback.auth.controller;

import com.hulahoop.redback.auth.model.service.AdminAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> req) {

        Map<String, String> response = adminAuthService.login(
                req.get("id"),
                req.get("password"));

        return ResponseEntity.ok(response);
    }
}