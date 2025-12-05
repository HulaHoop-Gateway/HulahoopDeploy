package com.hulahoop.redback.member.model.service;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.common.paging.dto.PageResponseDTO;
import com.hulahoop.redback.member.model.dao.MemberMapper;
import com.hulahoop.redback.member.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    // 기존 유지
    public List<MemberDTO> getAllMembers() {
        return memberMapper.selectAllMembers();
    }

    // 신규: 페이징 + 검색
    public PageResponseDTO<MemberDTO> getMembers(PageRequestDTO requestDTO) {

        List<MemberDTO> list =
                memberMapper.selectMembersPaged(requestDTO);

        long total =
                memberMapper.countMembers(requestDTO);

        return new PageResponseDTO<>(
                list,
                requestDTO.getPage(),
                requestDTO.getSize(),
                total
        );
    }
}
