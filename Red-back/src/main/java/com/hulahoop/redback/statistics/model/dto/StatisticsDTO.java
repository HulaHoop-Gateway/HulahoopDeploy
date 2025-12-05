package com.hulahoop.redback.statistics.model.dto;

import java.math.BigDecimal;

public class StatisticsDTO {
    private String merchantCode;
    private String merchantName;
    private String paymentDate;
    private int transactionCount;
    private BigDecimal transactionRatio;
    private BigDecimal totalAmount;
    private int refundCount;
    private BigDecimal refundAmount;
    private BigDecimal netAmount;
    private BigDecimal ratioPercentage;

    // ✅ Getter/Setter
    public String getMerchantCode() { return merchantCode; }
    public void setMerchantCode(String merchantCode) { this.merchantCode = merchantCode; }

    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public int getTransactionCount() { return transactionCount; }
    public void setTransactionCount(int transactionCount) { this.transactionCount = transactionCount; }

    public BigDecimal getTransactionRatio() { return transactionRatio; }
    public void setTransactionRatio(BigDecimal transactionRatio) { this.transactionRatio = transactionRatio; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public int getRefundCount() { return refundCount; }
    public void setRefundCount(int refundCount) { this.refundCount = refundCount; }

    public BigDecimal getRefundAmount() { return refundAmount; }
    public void setRefundAmount(BigDecimal refundAmount) { this.refundAmount = refundAmount; }

    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }

    public BigDecimal getRatioPercentage() { return ratioPercentage; }
    public void setRatioPercentage(BigDecimal ratioPercentage) { this.ratioPercentage = ratioPercentage; }
}
