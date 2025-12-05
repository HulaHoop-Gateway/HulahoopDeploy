package com.hulahoop.redback.member.controller;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.member.model.dto.MemberDTO;
import com.hulahoop.redback.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 기존 전체 조회 유지
    @GetMapping("/all")
    public ResponseEntity<?> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // 신규: 페이징 + 검색 지원
    @GetMapping
    public ResponseEntity<PageResponseDTO<MemberDTO>> getMembers(PageRequestDTO requestDTO) {

        PageResponseDTO<MemberDTO> result =
                memberService.getMembers(requestDTO);

        return ResponseEntity.ok(result);
    }
}
