package com.hulahoop.redback.monitor.model.dao;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper {

    // 페이징 + 검색용 조회
    List<Map<String, Object>> selectServersPaged(PageRequestDTO requestDTO);

    // total count 조회
    long countServers(PageRequestDTO requestDTO);
}
