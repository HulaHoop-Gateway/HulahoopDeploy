package com.hulahoop.redback.monitor.model.service;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.config.ServerConfig;
import com.hulahoop.redback.monitor.model.dao.BrandMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandMapper brandMapper;
    private final ServerConfig serverConfig;

    public BrandService(BrandMapper brandMapper, ServerConfig serverConfig) {
        this.brandMapper = brandMapper;
        this.serverConfig = serverConfig;
    }

    public PageResponseDTO<Map<String, Object>> getMergedServersPaged(PageRequestDTO requestDTO) {

        List<Map<String, Object>> brands =
                brandMapper.selectServersPaged(requestDTO);

        long total =
                brandMapper.countServers(requestDTO);

        List<Map<String, Object>> merged = brands.stream().map(b -> {
            Map<String, Object> map = new HashMap<>(b);

            serverConfig.getServers().stream()
                    .filter(s -> s.getBrandCode().equals(b.get("brandCode")))
                    .findFirst()
                    .ifPresentOrElse(
                            s -> {
                                map.put("baseUrl", s.getBaseUrl());
                                map.put("port", s.getPort());
                                map.put("healthPath", s.getHealthPath());
                            },
                            () -> {
                                map.put("baseUrl", null);
                                map.put("port", null);
                                map.put("healthPath", null);
                            }
                    );

            return map;
        }).collect(Collectors.toList());

        return new PageResponseDTO<>(
                merged,
                requestDTO.getPage(),
                requestDTO.getSize(),
                total
        );
    }
}
