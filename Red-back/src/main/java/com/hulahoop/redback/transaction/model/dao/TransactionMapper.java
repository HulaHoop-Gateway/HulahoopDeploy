package com.hulahoop.redback.transaction.model.dao;

import java.util.List;

import com.hulahoop.redback.common.paging.dto.PageRequestDTO;
import com.hulahoop.redback.transaction.model.dto.TransactionDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {

    // 전체 조회 유지
    List<TransactionDTO> selectAllTransactions();

    // 페이징 + 정렬 + 날짜 필터 대응
    List<TransactionDTO> selectTransactionsPaged(PageRequestDTO requestDTO);

    // 총 개수 조회
    long countTransactions(PageRequestDTO requestDTO);

    // insert 유지
    int insertTransaction(TransactionDTO dto);
}
