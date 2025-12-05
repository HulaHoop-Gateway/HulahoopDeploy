package com.hulahoop.redback.dashboard.service;

import com.hulahoop.redback.dashboard.model.dao.DashboardMapper;
import com.hulahoop.redback.dashboard.model.dto.DashboardDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    public DashboardService(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }

    @Transactional(readOnly = true)
    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        dto.setTotalMembers(dashboardMapper.countTotalMembers());
        dto.setTotalMerchants(dashboardMapper.countTotalMerchants());
        dto.setTotalApiRequests(dashboardMapper.countTotalApiRequests());
        dto.setTotalTransactions(dashboardMapper.countTotalTransactions());

        // 일별 데이터 빈 날짜 채우기 로직
        List<Map<String, Object>> rawDailyData = dashboardMapper.selectDailyTransactions();
        List<Map<String, Object>> filledDailyData = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");

        // DB 결과를 Map으로 변환하여 검색 용이하게 함
        Map<String, Double> dailyDataMap = new HashMap<>();
        for (Map<String, Object> data : rawDailyData) {
            String date = (String) data.get("date");
            Number amount = (Number) data.get("amount");
            dailyDataMap.put(date, amount.doubleValue());
        }

        // 최근 7일 (6일 전 ~ 오늘) 순회
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.format(formatter);

            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", dateStr);
            dayData.put("amount", dailyDataMap.getOrDefault(dateStr, 0.0));
            filledDailyData.add(dayData);
        }

        dto.setDailyTransactions(filledDailyData);
        dto.setMonthlyTransactions(dashboardMapper.selectMonthlyTransactions());
        dto.setCategoryRatio(dashboardMapper.selectCategoryRatio());

        return dto;
    }
}
