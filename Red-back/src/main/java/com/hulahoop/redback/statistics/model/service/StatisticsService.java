package com.hulahoop.redback.statistics.model.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.statistics.model.dao.StatisticsMapper;
import com.hulahoop.redback.statistics.model.dto.StatisticsDTO;

@Service
public class StatisticsService {

    private final StatisticsMapper statisticsMapper;

    public StatisticsService(StatisticsMapper statisticsMapper) {
        this.statisticsMapper = statisticsMapper;
    }

    public PageResponseDTO<StatisticsDTO> getStatistics(PageRequestDTO req) {

        List<StatisticsDTO> list = statisticsMapper.selectStatistics(req);
        long total = statisticsMapper.countStatistics(req);

        return new PageResponseDTO<>(list, req.getPage(), req.getSize(), total);
    }
}
