package com.hulahoop.redback.merchant.model.service;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.merchant.model.dao.MerchantMapper;
import com.hulahoop.redback.merchant.model.dto.MerchantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    // ✅ 페이징 + 검색
    public PageResponseDTO<MerchantDTO> getMerchants(PageRequestDTO requestDTO) {

        List<MerchantDTO> list = merchantMapper.selectMerchantsPaged(requestDTO);

        long total = merchantMapper.countMerchants(requestDTO);

        return new PageResponseDTO<>(
                list,
                requestDTO.getPage(),
                requestDTO.getSize(),
                total);
    }

    // ✅ 기존 기능 유지
    public String findMerchantCodeByName(String merchantName) {
        return merchantMapper.findMerchantCodeByName(merchantName);
    }

    // ✅ 가맹점 생성
    public void createMerchant(MerchantDTO merchantDTO) {
        // 🛡️ 안전장치: brandCode가 없으면 기본값 설정 (DB 제약조건 위반 방지)
        if (merchantDTO.getBrandCode() == null || merchantDTO.getBrandCode().trim().isEmpty()) {
            merchantDTO.setBrandCode("NB01"); // 기본값: 노바시네마
        }
        merchantMapper.insertMerchant(merchantDTO);
    }

    // ✅ 가맹점 삭제 (이용내역 체크)
    public void deleteMerchant(String merchantCode) {
        try {
            merchantMapper.deleteMerchant(merchantCode);
        } catch (DataIntegrityViolationException e) {
            // 외래 키 제약 조건 위반 (이용내역이 존재하는 경우)
            throw new IllegalStateException("이용내역이 존재하는 가맹점은 삭제할 수 없습니다.");
        }
    }

    // ✅ 가맹점 수정 (브랜드 코드 체크)
    public void updateMerchant(MerchantDTO merchantDTO) {
        try {
            merchantMapper.updateMerchant(merchantDTO);
        } catch (DataIntegrityViolationException e) {
            // 외래 키 제약 조건 위반 (존재하지 않는 브랜드 코드 등)
            if (e.getMessage().contains("brand") || e.getMessage().contains("BRAND")) {
                throw new IllegalStateException("존재하지 않는 브랜드 코드입니다. BR001(노바시네마) 또는 BR002(바이크웨이)만 사용 가능합니다.");
            }
            throw new IllegalStateException("가맹점 수정에 실패했습니다. 입력값을 확인해주세요.");
        }
    }
}
