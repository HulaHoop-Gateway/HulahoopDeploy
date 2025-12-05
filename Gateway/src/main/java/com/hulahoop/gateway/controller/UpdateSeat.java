package com.hulahoop.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/internal")
public class UpdateSeat {
    @PostMapping("/seat-updated")
    public ResponseEntity<Void> handleSeatUpdate(@RequestBody Map<String, Object> payload) {
        Integer scheduleNum = (Integer) payload.get("scheduleNum");

        System.out.println("✅ 좌석 업데이트 감지됨: scheduleNum = " + scheduleNum);

        return ResponseEntity.ok().build();
}
}
