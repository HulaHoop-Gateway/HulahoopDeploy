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

    // ✅ 만료된 예약 조회 (시작 시간 지남)
    List<ReservationDTO> findExpiredReservationsV2(String currentDateTime);

    // ✅ 종료된 예약 조회 (종료 시간 지남)
    List<ReservationDTO> findFinishedReservations(String currentDateTime);
}
