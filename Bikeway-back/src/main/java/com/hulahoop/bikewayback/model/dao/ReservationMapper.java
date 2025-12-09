package com.hulahoop.bikewayback.model.dao;

import com.hulahoop.bikewayback.model.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {
    List<ReservationDTO> findByMemberCode(@Param("memberCode") int memberCode);

    ReservationDTO findByRecordNum(@Param("recordNum") int recordNum);

    ReservationDTO findByTransactionNum(@Param("transactionNum") Long transactionNum);

    int updateReservationState(@Param("recordNum") int recordNum, @Param("state") String state);

    // 만료된 예약 조회 (현재 날짜 + 현재 시간 기준)
    List<ReservationDTO> findExpiredReservations(@Param("currentDate") String currentDate,
            @Param("currentTime") String currentTime);
}
