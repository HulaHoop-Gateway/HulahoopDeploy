package com.hulahoop.redback.member.model.dao;

import java.util.List;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    // 기존 전체 조회 유지
    List<MemberDTO> selectAllMembers();

    // 신규: 페이징 + 검색
    List<MemberDTO> selectMembersPaged(PageRequestDTO requestDTO);

    long countMembers(PageRequestDTO requestDTO);

    // 기존 유지
    String findMemberCodeByPhone(String phoneNumber);
}
