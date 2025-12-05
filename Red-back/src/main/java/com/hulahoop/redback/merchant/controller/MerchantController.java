package com.hulahoop.redback.merchant.controller;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.merchant.model.dto.MerchantDTO;
import com.hulahoop.redback.merchant.model.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    // ✅ 페이징 + 검색 지원
    @GetMapping
    public ResponseEntity<PageResponseDTO<MerchantDTO>> getMerchants(PageRequestDTO pageRequestDTO) {
        PageResponseDTO<MerchantDTO> result = merchantService.getMerchants(pageRequestDTO);
        return ResponseEntity.ok(result);
    }

    // ✅ 가맹점 생성
    @PostMapping
    public ResponseEntity<String> createMerchant(@RequestBody MerchantDTO merchantDTO) {
        merchantService.createMerchant(merchantDTO);
        return ResponseEntity.ok("가맹점이 생성되었습니다.");
    }

    // ✅ 가맹점 삭제
    @DeleteMapping("/{merchantCode}")
    public ResponseEntity<String> deleteMerchant(@PathVariable String merchantCode) {
        try {
            merchantService.deleteMerchant(merchantCode);
            return ResponseEntity.ok("가맹점이 삭제되었습니다.");
        } catch (IllegalStateException e) {
            // 이용내역이 존재하는 경우
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 기타 오류
            return ResponseEntity.badRequest().body("삭제에 실패했습니다.");
        }
    }

    // ✅ 가맹점 수정
    @PutMapping("/{merchantCode}")
    public ResponseEntity<String> updateMerchant(@PathVariable String merchantCode,
            @RequestBody MerchantDTO merchantDTO) {
        try {
            merchantDTO.setMerchantCode(merchantCode); // 경로 변수에서 받은 merchantCode를 DTO에 설정
            merchantService.updateMerchant(merchantDTO);
            return ResponseEntity.ok("가맹점이 성공적으로 수정되었습니다.");
        } catch (IllegalStateException e) {
            // 브랜드 코드 오류 등
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 기타 오류
            return ResponseEntity.badRequest().body("가맹점 수정에 실패했습니다.");
        }
    }
}
