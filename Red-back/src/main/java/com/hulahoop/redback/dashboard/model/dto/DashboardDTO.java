package com.hulahoop.redback.dashboard.model.dto;

import java.util.List;
import java.util.Map;

public class DashboardDTO {
    private long totalMembers;
    private long totalMerchants;
    private long totalApiRequests;
    private long totalTransactions;

    private List<Map<String, Object>> dailyTransactions;
    private List<Map<String, Object>> monthlyTransactions;
    private List<Map<String, Object>> categoryRatio;
    private List<Map<String, Object>> movieBikeCategoryRatio;

    public DashboardDTO() {
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public long getTotalMerchants() {
        return totalMerchants;
    }

    public void setTotalMerchants(long totalMerchants) {
        this.totalMerchants = totalMerchants;
    }

    public long getTotalApiRequests() {
        return totalApiRequests;
    }

    public void setTotalApiRequests(long totalApiRequests) {
        this.totalApiRequests = totalApiRequests;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public List<Map<String, Object>> getDailyTransactions() {
        return dailyTransactions;
    }

    public void setDailyTransactions(List<Map<String, Object>> dailyTransactions) {
        this.dailyTransactions = dailyTransactions;
    }

    public List<Map<String, Object>> getMonthlyTransactions() {
        return monthlyTransactions;
    }

    public void setMonthlyTransactions(List<Map<String, Object>> monthlyTransactions) {
        this.monthlyTransactions = monthlyTransactions;
    }

    public List<Map<String, Object>> getCategoryRatio() {
        return categoryRatio;
    }

    public void setCategoryRatio(List<Map<String, Object>> categoryRatio) {
        this.categoryRatio = categoryRatio;
    }

    public List<Map<String, Object>> getMovieBikeCategoryRatio() {
        return movieBikeCategoryRatio;
    }

    public void setMovieBikeCategoryRatio(List<Map<String, Object>> movieBikeCategoryRatio) {
        this.movieBikeCategoryRatio = movieBikeCategoryRatio;
    }
}
