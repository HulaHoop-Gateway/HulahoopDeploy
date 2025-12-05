package com.hulahoop.redback.statistics.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.hulahoop.redback.statistics.model.dto.StatisticsDTO;
import com.hulahoop.redback.common.paging.dto.PageRequestDTO;

@Mapper
public interface StatisticsMapper {

    List<StatisticsDTO> selectStatistics(PageRequestDTO pageRequestDTO);

    long countStatistics(PageRequestDTO pageRequestDTO);
}
