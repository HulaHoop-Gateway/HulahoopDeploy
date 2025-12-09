package com.hulahoop.bikewayback.service;

import com.hulahoop.bikewayback.model.dao.BicycleMapper;
import com.hulahoop.bikewayback.model.dao.ReservationMapper;
import com.hulahoop.bikewayback.model.dto.ReservationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationExpiryScheduler {

    private static final Logger log = LoggerFactory.getLogger(ReservationExpiryScheduler.class);

    private final ReservationMapper reservationMapper;
    private final BicycleMapper bicycleMapper;

    public ReservationExpiryScheduler(ReservationMapper reservationMapper, BicycleMapper bicycleMapper) {
        this.reservationMapper = reservationMapper;
        this.bicycleMapper = bicycleMapper;
    }

    /**
     * 매 1분마다 시작 시간이 지난 예약을 찾아 자동으로 상태를 변경합니다.
     * - 예약 상태: 예약완료 → 완료됨 (이용 중/완료)
     * - 자전거 상태: Reserved → Reserved (시작 시간부터는 계속 예약 상태 유지, 종료 시간에 Available로)
     */
    @Scheduled(fixedRate = 60000) // 1분 (60,000 ms)
    public void processExpiredReservations() {
        try {
            log.info("🔍 만료된 예약 검색 시작...");

            // ✅ 현재 시간 (KST) 구하기
            java.time.ZonedDateTime now = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Seoul"));
            String currentDate = now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String currentTime = now.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));

            log.info("📅 기준 시간: {} {}", currentDate, currentTime);

            List<ReservationDTO> expiredReservations = reservationMapper.findExpiredReservationsV2(currentDate,
                    currentTime);

            if (expiredReservations.isEmpty()) {
                log.info("✅ 만료된 예약 없음");
                return;
            }

            log.info("⏰ 시작 시간이 지난 예약 {}건 발견", expiredReservations.size());

            int successCount = 0;
            for (ReservationDTO reservation : expiredReservations) {
                try {
                    // ✅ 예약 상태만 업데이트: 예약완료 → 완료됨 (이용내역으로 이동)
                    // 자전거는 아직 Reserved 상태 유지 (실제 사용 중)
                    int updated = reservationMapper.updateReservationState(reservation.getRecordNum(), "완료됨");

                    if (updated > 0) {
                        // ❌ 자전거 상태는 그대로 유지 (Reserved)
                        // bicycleMapper.updateBicycleStatus(reservation.getBicycleCode(), "Available");

                        // ✅ Admin 서버 상태 업데이트 (P → S)
                        updateAdminTransactionStatus(reservation.getTransactionNum());

                        successCount++;

                        log.info("✔️ 예약 #{} 이용 시작 처리 완료 (자전거: {}, 트랜잭션: {})",
                                reservation.getRecordNum(), reservation.getBicycleCode(),
                                reservation.getTransactionNum());
                    }
                } catch (Exception e) {
                    log.error("❌ 예약 #{} 처리 실패: {}", reservation.getRecordNum(), e.getMessage());
                }
            }

            log.info("🎉 이용 시작 처리 완료: {}/{} 건 성공", successCount, expiredReservations.size());

        } catch (Exception e) {
            log.error("❌ 만료 예약 처리 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    /**
     * Admin 서버에 트랜잭션 상태 업데이트 (P → S)
     */
    private void updateAdminTransactionStatus(Long transactionNum) {
        if (transactionNum == null) {
            return;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://red-back:8000/api/transactions/update-status";

            Map<String, Object> payload = new HashMap<>();
            payload.put("transactionNum", transactionNum);
            payload.put("status", "S"); // Success/Complete

            restTemplate.postForObject(url, payload, String.class);
            log.info("✅ Admin 서버 상태 업데이트 성공: transactionNum={}", transactionNum);
        } catch (Exception e) {
            log.error("❌ Admin 서버 상태 업데이트 실패: transactionNum={}, error={}",
                    transactionNum, e.getMessage());
        }
    }
}
