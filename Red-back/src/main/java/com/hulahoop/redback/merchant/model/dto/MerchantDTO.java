package com.hulahoop.redback.merchant.model.dto;

/**
 * MerchantDTO
 *
 * 가맹점 운영 정보를 담는 데이터 전송 객체 (DTO)
 * - DB의 T_Merchant, T_Brand, T_Category 테이블 조인 결과와 매핑됨
 */
public class MerchantDTO {

    // ✅ 가맹점 코드
    private String merchantCode;

    // ✅ 가맹점 이름
    private String merchantName;

    // ✅ 사업자번호
    private String businessId;

    // ✅ 카테고리명
    private String categoryName;

    // ✅ 등록일
    private String registrationDate;

    // ✅ 종료일
    private String terminationDate;

    // ✅ 계약상태
    private String contractStatus;

    // ✅ 카테고리 코드 (Movie, Bike 등)
    private String categoryCode;

    // 🔥🔥🔥 추가된 필드 (프론트 필터링에 반드시 필요)
    private String brandCode;   // 예: NB01
    private String brandName;   // 예: 노바시네마


    // ───────────────────────────────
    // Getter / Setter
    // ───────────────────────────────

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getBrandCode() {        // ← 추가
        return brandCode;
    }

    public void setBrandCode(String brandCode) { // ← 추가
        this.brandCode = brandCode;
    }

    public String getBrandName() {        // ← 추가
        return brandName;
    }

    public void setBrandName(String brandName) { // ← 추가
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "MerchantDTO{" +
                "merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", businessId='" + businessId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", terminationDate='" + terminationDate + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", brandCode='" + brandCode + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
