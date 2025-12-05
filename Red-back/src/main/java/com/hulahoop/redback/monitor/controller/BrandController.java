package com.hulahoop.redback.monitor.controller;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.monitor.model.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servers")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<?>> getServerList(PageRequestDTO requestDTO) {
        return ResponseEntity.ok(brandService.getMergedServersPaged(requestDTO));
    }
}
