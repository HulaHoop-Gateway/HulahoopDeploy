package com.hulahoop.redback.transaction.model.service;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.member.model.dao.MemberMapper;
import com.hulahoop.redback.merchant.model.dao.MerchantMapper;
import com.hulahoop.redback.transaction.model.dao.TransactionMapper;
import com.hulahoop.redback.transaction.model.dto.TransactionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    // 기존 전체 조회 유지
    public List<TransactionDTO> getAllTransactions() {
        return transactionMapper.selectAllTransactions();
    }

    // 신규: 페이징 + 정렬 + 날짜 필터 + merchant 필터 대응
    public PageResponseDTO<TransactionDTO> getTransactions(PageRequestDTO requestDTO) {

        List<TransactionDTO> list =
                transactionMapper.selectTransactionsPaged(requestDTO);

        long total =
                transactionMapper.countTransactions(requestDTO);

        return new PageResponseDTO<>(
                list,
                requestDTO.getPage(),
                requestDTO.getSize(),
                total
        );
    }

    // ✅ phoneNum을 memberCode로 변환, merchant_name을 merchant_code로 변환, paymentDate 설정
    public int insertTransaction(TransactionDTO dto) {
        // phoneNum이 있고 memberCode가 없으면 phoneNum으로 memberCode 조회
        if (dto.getPhoneNum() != null && !dto.getPhoneNum().isEmpty() 
                && (dto.getMemberCode() == null || dto.getMemberCode().isEmpty())) {
            String memberCode = memberMapper.findMemberCodeByPhone(dto.getPhoneNum());
            if (memberCode != null) {
                dto.setMemberCode(memberCode);
            } else {
                log.warn("⚠️ memberCode 조회 실패: phoneNum={}", dto.getPhoneNum());
            }
        }

        // merchantCode가 지점명(merchant_name) 형식인 경우 merchant_code로 변환
        if (dto.getMerchantCode() != null && !dto.getMerchantCode().isEmpty()) {
            // merchant_code 형식이 아닌 것 같으면 (예: "노바시네마 하남1점" 같은 지점명)
            // merchant_name으로 조회해서 실제 merchant_code로 변환
            String actualMerchantCode = merchantMapper.findMerchantCodeByName(dto.getMerchantCode());
            if (actualMerchantCode != null && !actualMerchantCode.isEmpty()) {
                log.info("✅ merchant_name을 merchant_code로 변환: {} -> {}", dto.getMerchantCode(), actualMerchantCode);
                dto.setMerchantCode(actualMerchantCode);
            } else {
                // 변환 실패 시 원래 값이 이미 merchant_code일 수도 있으므로 그대로 사용
                log.warn("⚠️ merchant_name을 merchant_code로 변환 실패: {}, 원래 값 사용", dto.getMerchantCode());
            }
        }

        // paymentDate가 없으면 현재 시간으로 설정
        if (dto.getPaymentDate() == null || dto.getPaymentDate().isEmpty()) {
            dto.setPaymentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        return transactionMapper.insertTransaction(dto);
    }
}
