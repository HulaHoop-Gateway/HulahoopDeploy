package com.hulahoop.redback.dashboard.model.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {
    long countTotalMembers();

    long countTotalMerchants();

    long countTotalApiRequests();

    long countTotalTransactions();

    List<Map<String, Object>> selectDailyTransactions();

    List<Map<String, Object>> selectMonthlyTransactions();

    List<Map<String, Object>> selectCategoryRatio();
}
