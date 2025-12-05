package com.hulahoop.redback.statistics.controller;

import org.springframework.web.bind.annotation.*;
import com.hulahoop.redback.statistics.model.dto.StatisticsDTO;
import com.hulahoop.redback.statistics.model.service.StatisticsService;
import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public PageResponseDTO<StatisticsDTO> getStatistics(PageRequestDTO pageRequestDTO) {
        return statisticsService.getStatistics(pageRequestDTO);
    }
}
